package com.aurelia.loaning.db;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.aurelia.loaning.reflection.ClasspathScanner;
import com.aurelia.loaning.reflection.TableDeclarationPreparator;

public class LoanDatabaseSQLite extends SQLiteOpenHelper {

	private Context context;

	public LoanDatabaseSQLite(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		TableDeclarationPreparator tablePreparator = new TableDeclarationPreparator(new ClasspathScanner());
		List<String> tableDeclarations;
		try {
			tableDeclarations = tablePreparator.createTablesDeclaration(context);
			for (String tableDeclaration : tableDeclarations) {
				db.execSQL(tableDeclaration);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
