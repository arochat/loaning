package com.aurelia.loaning.reflection;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.aurelia.loaning.db.annotation.Column;
import com.aurelia.loaning.db.annotation.Table;

public class TableDeclarationPreparator {

	private final static String APPLICATION_ROOT_PACKAGE = "com.aurelia.loaning";
	private final static String EXTENDED_LOOKUP_PACKAGE_NAME = "com.aurelia.loaning.db.entity";

	private ClasspathScanner scanner;

	public TableDeclarationPreparator(ClasspathScanner scanner) {
		this.scanner = scanner;
	}

	public List<String> createTablesDeclaration(Context context) throws IOException, ClassNotFoundException {
		List<String> tableDeclarations = new ArrayList<String>(10);

		Iterable<Class> entities = findEntities(context);
		for (Class entity : entities) {
			Iterable<Field> annotatedFields = scanner.findAnnotatedFields(entity, Column.class);

			String tableDeclaration = "CREATE TABLE IF NOT EXISTS ";

			// create corresponding table declaration here
			Annotation annotation = entity.getAnnotation(Table.class);
			if (annotation instanceof Table) {
				tableDeclaration += ((Table) annotation).name() + " (";
			}

			// add fields declarations
			for (Field field : annotatedFields) {
				Annotation annotationOnField = field.getAnnotation(Column.class);
				if (annotationOnField instanceof Column) {
					Column column = (Column) annotationOnField;
					tableDeclaration += column.name() + " ";
					tableDeclaration += column.type();
					if (column.primaryKeyAutoIncrement()) {
						tableDeclaration += " PRIMARY KEY AUTOINCREMENT";
					}
					if (!column.nullable()) {
						tableDeclaration += " NOT NULL";
					}
					tableDeclaration += ",";
				}
			}
			// remove last coma
			tableDeclaration = tableDeclaration.substring(0, tableDeclaration.length() - 1);
			tableDeclaration += ");";
			tableDeclarations.add(tableDeclaration);
		}
		return tableDeclarations;
	}

	private Iterable<Class> findEntities(Context context) throws IOException, ClassNotFoundException {
		try {
			return scanner.findAnnotatedClasses(context, APPLICATION_ROOT_PACKAGE, EXTENDED_LOOKUP_PACKAGE_NAME,
					Table.class);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
