package com.aurelia.loaning.reflection;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public String getTableName(Class entity) {
		Annotation annotation = entity.getAnnotation(Table.class);
		return ((Table) annotation).name();
	}

	public String getIdColumnName(Class entity) {
		String idColumnName = "";
		Iterable<Field> annotatedFields = findAnnotatedFields(entity, Column.class);
		for (Field field : annotatedFields) {
			Annotation annotationOnField = field.getAnnotation(Column.class);
			Column column = (Column) annotationOnField;
			if (column.primaryKeyAutoIncrement()) {
				idColumnName = column.name();
			}
		}
		return idColumnName;
	}

	public <T> Map<String, Object> getEntityContent(Class entity, T filledObject) throws IllegalArgumentException,
			SecurityException, IllegalAccessException, NoSuchFieldException {
		Map<String, Object> objectContent = new HashMap<String, Object>();
		Iterable<Field> annotatedFields = findAnnotatedFields(entity, Column.class);
		for (Field field : annotatedFields) {
			Annotation annotationOnField = field.getAnnotation(Column.class);
			if (annotationOnField instanceof Column) {
				objectContent.put(((Column) annotationOnField).name(), filledObject.getClass()
						.getField(field.getName()).get(filledObject));
			}
		}
		return objectContent;
	}

	public List<String> getColumnNames(Class entity) {
		List<String> columnNames = new ArrayList<String>();

		Iterable<Field> annotatedFields = findAnnotatedFields(entity, Column.class);
		for (Field field : annotatedFields) {
			Annotation annotationOnField = field.getAnnotation(Column.class);
			if (annotationOnField instanceof Column) {
				Column column = (Column) annotationOnField;
				columnNames.add(column.name());
			}
		}
		return columnNames;
	}

	public List<String> createTablesDeclaration(Context context) throws IOException, ClassNotFoundException {
		List<String> tableDeclarations = new ArrayList<String>(10);

		Iterable<Class> entities = findEntities(context);
		for (Class entity : entities) {
			Iterable<Field> annotatedFields = findAnnotatedFields(entity, Column.class);

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

	private Iterable<Field> findAnnotatedFields(Class entity, Class annotation) {
		return scanner.findAnnotatedFields(entity, annotation);
	}
}
