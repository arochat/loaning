package com.aurelia.loaning.reflection;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import com.aurelia.loaning.db.annotation.Column;
import com.aurelia.loaning.db.annotation.Table;

public class TablePreparator {

	private final static String rootPackage = "com.aurelia.loaning";

	private ClasspathScanner scanner;

	public TablePreparator(ClasspathScanner scanner) {
		this.scanner = scanner;
	}

	public List<String> createTablesDeclaration() throws IOException, ClassNotFoundException {
		List<String> tableDeclarations = Collections.emptyList();

		List<Class> entities = findEntities();
		for (Class entity : entities) {
			List<Field> annotatedFields = scanner.findAnnotatedFields(entity, Column.class);

			String tableDeclaration = "CREATE TABLE ";

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
					tableDeclaration += column.type() + " ";
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
			tableDeclaration = tableDeclaration.substring(0, tableDeclaration.length() - 2);
			tableDeclaration += ");";
			tableDeclarations.add(tableDeclaration);
		}
		return tableDeclarations;
	}

	private List<Class> findEntities() throws IOException, ClassNotFoundException {
		return scanner.findAnnotatedClasses(Table.class, rootPackage);
	}

}
