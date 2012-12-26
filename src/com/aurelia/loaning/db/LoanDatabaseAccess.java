package com.aurelia.loaning.db;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aurelia.loaning.db.entity.Loan;
import com.aurelia.loaning.reflection.ClasspathScanner;
import com.aurelia.loaning.reflection.TableDeclarationPreparator;

public class LoanDatabaseAccess {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "loan.db";

	private final DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:MM:SS.SSS");

	private LoanDatabaseSQLite loanDb;

	private DatabaseAccess databaseAccess;

	public LoanDatabaseAccess(Context context) {
		loanDb = new LoanDatabaseSQLite(context, DB_NAME, null, DB_VERSION);
		databaseAccess = new DatabaseAccess(new TableDeclarationPreparator(new ClasspathScanner()), loanDb, formatter);
	}

	public void open() {
		databaseAccess.open();
	}

	public void close() {
		databaseAccess.close();
	}

	public SQLiteDatabase getDb() {
		return databaseAccess.getDb();
	}

	public long insert(Loan loan) {
		return databaseAccess.insert(loan, formatter);
	}

	public List<Object> selectAllLoans() {
		return databaseAccess.read(Loan.class);
	}

}
