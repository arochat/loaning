package com.aurelia.loaning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LoanDatabaseSQLite extends SQLiteOpenHelper {

	public LoanDatabaseSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TablePreparator tablePreparator = new TablePreparator(new
		// ClasspathScanner());

		// List<String> tableDeclarations =
		// tablePreparator.createTablesDeclaration();
		String tableDeclaration = "CREATE TABLE LOAN (" + //
				"loan_id integer PRIMARY KEY AUTOINCREMENT, " + //
				"source text NOT NULL, " + //
				"destination text NOT NULL, " + //
				"start_date text NOT NULL, " + //
				"end_date text NOT NULL, " + //
				"is_contact integer NOT NULL," + //
				"type text, " + //
				"description text NOT NULL);";

		db.execSQL(tableDeclaration);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
