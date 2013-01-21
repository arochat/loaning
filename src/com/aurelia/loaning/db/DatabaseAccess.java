package com.aurelia.loaning.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aurelia.loaning.db.annotation.Column;
import com.aurelia.loaning.reflection.TableDeclarationPreparator;

public class DatabaseAccess {

	private SQLiteDatabase db;

	private TableDeclarationPreparator tablePreparator;

	private DateTimeFormatter formatter;

	private SQLiteOpenHelper dbHelper;

	public DatabaseAccess(TableDeclarationPreparator tablePreparator, SQLiteOpenHelper dbHelper,
			DateTimeFormatter formatter) {
		this.tablePreparator = tablePreparator;
		this.dbHelper = dbHelper;
		this.formatter = formatter;
	}

	public void open() {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void removeAll(Object o) {
		String tableName = tablePreparator.getTableName(o.getClass());
		db.delete(tableName, null, null);
	}

	public long delete(Object o, String whereClause, String... whereArgs) {
		String tableName = tablePreparator.getTableName(o.getClass());
		return db.delete(tableName, whereClause, whereArgs);
	}

	public long insert(Object o, DateTimeFormatter formatter) {
		try {
			String tableName = tablePreparator.getTableName(o.getClass());

			Map<String, Object> objectContent = tablePreparator.getEntityContent(o.getClass(), o);

			String idColumnName = tablePreparator.getIdColumnName(o.getClass());

			ContentValues values = new ContentValues();

			Iterator<Map.Entry<String, Object>> it = objectContent.entrySet().iterator();
			String value = "";
			while (it.hasNext()) {
				Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) it.next();
				if (!pairs.getKey().equals(idColumnName)) {
					if (pairs.getValue() instanceof DateTime) {
						DateTime dt = (DateTime) pairs.getValue();
						if (pairs.getValue() != null) {
							value = dt.toString(formatter);
						}
					} else {
						if (pairs.getValue() != null) {
							value = pairs.getValue().toString();
						} else {
							value = "";
						}
					}
					values.put((String) pairs.getKey(), value);
				}
			}
			return db.insert(tableName, null, values);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1l;
	}

	public List<Object> read(Class entity) {
		List<String> columns = tablePreparator.getColumnNames(entity);
		String tableName = tablePreparator.getTableName(entity);
		String[] columnNames = new String[columns.size()];
		Cursor cursor = db.query(tableName, columns.toArray(columnNames), null, null, null, null, "end_date ASC");
		try {
			return cursorToObjects(cursor, entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}

	private List<Object> cursorToObjects(Cursor c, Class entity) throws IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException,
			NoSuchFieldException {

		if (c.getCount() == 0)
			return new ArrayList<Object>();

		c.moveToFirst();

		List<Object> objects = new ArrayList<Object>(c.getCount());

		Map<String, Method> setterMap = new HashMap<String, Method>();

		// put all setters in a map <fieldName --> method>
		Method[] methods = entity.getMethods();
		String methodName = "";
		String tmpFieldName = "";
		for (Method method : methods) {
			methodName = method.getName();
			if (methodName.startsWith("set")) {
				tmpFieldName = methodName.substring(3, methodName.length());
				tmpFieldName = tmpFieldName.substring(0, 1).toLowerCase() + tmpFieldName.substring(1);
				setterMap.put(tmpFieldName, method);
			}
		}

		do {
			// now call each setter with the value found in the cursor as
			// parameter
			Iterator<Map.Entry<String, Method>> it = setterMap.entrySet().iterator();

			// create object to return
			Object myObject = entity.newInstance();

			Field field;
			String columnName = "";
			Column column;
			Method method;

			while (it.hasNext()) {
				Map.Entry<String, Method> pairs = (Map.Entry<String, Method>) it.next();

				field = entity.getDeclaredField(pairs.getKey());
				column = field.getAnnotation(Column.class);
				method = pairs.getValue();

				if (column != null) {
					columnName = column.name();
					if (field.getType().equals(Boolean.class)) {
						method.invoke(myObject, c.getInt(c.getColumnIndex(columnName)) == 1 ? true : false);

					} else if (field.getType().equals(String.class)) {
						method.invoke(myObject, c.getString(c.getColumnIndex(columnName)));

					} else if (field.getType().equals(DateTime.class)) {
						method.invoke(myObject, formatter.parseDateTime(c.getString(c.getColumnIndex(columnName))));

					} else if (field.getType().equals(Long.class)) {
						method.invoke(myObject, c.getLong(c.getColumnIndex(columnName)));

					} else {
						// TODO error
					}
				}
			}
			objects.add(myObject);

		} while (c.moveToNext());

		c.close();
		return objects;
	}

	public TableDeclarationPreparator getTablePreparator() {
		return tablePreparator;
	}

}
