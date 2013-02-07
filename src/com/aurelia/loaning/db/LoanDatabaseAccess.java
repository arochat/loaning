package com.aurelia.loaning.db;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.ContentValues;
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

	public List<Object> selectLoansWithStatus(int status) {
		return databaseAccess.select(Loan.class, "status=" + status);
	}

	public void removeAll() {
		databaseAccess.removeAll(new Loan());
	}

	public long delete(Loan loan) {
		String idName = databaseAccess.getTablePreparator().getIdColumnName(loan.getClass());
		return databaseAccess.delete(loan, idName + "=?", new String[] { loan.getId().toString() });
	}

	public int updateStatus(Loan loan, int newStatus) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("STATUS", newStatus);
		return databaseAccess.update(loan, contentValues, "loan_id=" + loan.getId(), null);
	}

	// tmp hack to update all status to ACTIVE
	// public void update(Loan loan) {

	// tmp hack to add column 'STATUS'
	// String alterTable1 = "ALTER TABLE LOAN ADD status INTEGER;";
	// getDb().execSQL(alterTable1);

	// filling the new column
	// ContentValues contentValues = new ContentValues();
	// contentValues.put("STATUS", LoanStatus.ACTIVE.getValue());
	// databaseAccess.update(loan, contentValues, null, null);
	// }
}
