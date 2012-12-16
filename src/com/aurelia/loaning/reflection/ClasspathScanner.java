package com.aurelia.loaning.reflection;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.aurelia.loaning.db.annotation.Table;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class ClasspathScanner {

	public List<Class> findAnnotatedClasses(Context context, String applicationPackageName, String extendedPackageName,
			Class annotation) throws IOException, URISyntaxException, ClassNotFoundException, NameNotFoundException {

		String apkName = context.getPackageManager().getApplicationInfo(applicationPackageName, 0).sourceDir;
		DexFile dexFile = new DexFile(apkName);
		PathClassLoader classLoader = new PathClassLoader(apkName, Thread.currentThread().getContextClassLoader());

		List<Class> classes = new ArrayList<Class>();
		Enumeration<String> entries = dexFile.entries();

		while (entries.hasMoreElements()) {

			String entry = entries.nextElement();
			// only check items that exist in source package and not in
			// libraries, etc.
			if (entry.startsWith(extendedPackageName)) {

				Class<?> entryClass = classLoader.loadClass(entry);
				if (entryClass != null) {
					Annotation declaredAnnotation = entryClass.getAnnotation(annotation);
					if (declaredAnnotation instanceof Table) { // TODO : do not
																// hardcode
																// Table here
						classes.add(entryClass);
					}
				}
			}
		}

		return classes;
	}

	public Iterable<Field> findAnnotatedFields(final Class entity, final Class annotation) {
		Field[] declaredFields = entity.getFields();
		List<Field> fieldList = Arrays.asList(declaredFields);

		Predicate<Field> isAnnotated = new Predicate<Field>() {
			@Override
			public boolean apply(Field field) {
				return field.isAnnotationPresent(annotation);
			}
		};
		Iterable<Field> annotatedFields = Iterables.filter(fieldList, isAnnotated);

		return annotatedFields;
	}
}
