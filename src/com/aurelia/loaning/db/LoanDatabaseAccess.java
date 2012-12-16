package com.aurelia.loaning.db;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aurelia.loaning.db.entity.Loan;

public class LoanDatabaseAccess {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "loan.db";

	private final DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:MM:SS.SSS");

	private SQLiteDatabase db;

	private LoanDatabaseSQLite loanDb;

	public LoanDatabaseAccess(Context context) {
		loanDb = new LoanDatabaseSQLite(context, DB_NAME, null, DB_VERSION);
	}

	public void open() {
		db = loanDb.getWritableDatabase();
		// initDb();
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public long insert(Loan loan) {

		ContentValues values = new ContentValues();
		values.put("source", loan.source);
		values.put("destination", loan.destination);
		values.put("start_date", loan.starteDate.toString(formatter));
		values.put("end_date", loan.endDate.toString(formatter));
		values.put("description", loan.description);
		values.put("is_contact", false);

		return db.insert("LOAN", null, values);
	}

	public List<Loan> read() {
		Cursor cursor = db.query("LOAN", new String[] { "source", "destination", "start_date", "end_date",
				"is_contact", "type", "description" }, null, null, null, null, null);
		return cursorToLoans(cursor);
	}

	private List<Loan> cursorToLoans(Cursor c) {

		List<Loan> loans = new ArrayList<Loan>(10);

		if (c.getCount() == 0)
			return null;

		c.moveToFirst();
		do {
			Loan loan = new Loan(c.getString(c.getColumnIndex("source")),//
					c.getString(c.getColumnIndex("destination")), //
					formatter.parseDateTime(c.getString(c.getColumnIndex("start_date"))), //
					formatter.parseDateTime(c.getString(c.getColumnIndex("end_date"))), //
					c.getInt(c.getColumnIndex("is_contact")) == 1 ? true : false, //
					c.getString(c.getColumnIndex("type")),//
					c.getString(c.getColumnIndex("description")));
			loans.add(loan);

		} while (c.moveToNext());

		c.close();
		return loans;
	}
}
