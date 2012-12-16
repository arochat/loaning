package com.aurelia.loaning.reflection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.aurelia.loaning.db.annotation.Table;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class ClasspathScanner {

	public List<Class> findAnnotatedClasses(Class annotation, String rootPackage) throws IOException,
			ClassNotFoundException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = rootPackage.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			if (!directory.getPath().contains("test")) {
				classes.addAll(findClasses(directory, rootPackage));
			}
		}

		Predicate<Class> isAnnotated = new Predicate<Class>() {
			@Override
			public boolean apply(Class clazz) {
				return clazz.isAnnotationPresent(Table.class);
			}
		};
		Iterable<Class> annotatedClasses = Iterables.filter(classes, isAnnotated);

		return (List<Class>) annotatedClasses;
	}

	public List<Field> findAnnotatedFields(final Class entity, final Class annotation) {
		Field[] declaredFields = entity.getFields();
		List<Field> fieldList = Arrays.asList(declaredFields);

		Predicate<Field> isAnnotated = new Predicate<Field>() {
			@Override
			public boolean apply(Field field) {
				return field.isAnnotationPresent(annotation);
			}
		};
		Iterable<Field> annotatedFields = Iterables.filter(fieldList, isAnnotated);

		return (List<Field>) annotatedFields;
	}

	private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		if (packageName.contains("test")) {
			return Collections.EMPTY_LIST;
		}
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

}
