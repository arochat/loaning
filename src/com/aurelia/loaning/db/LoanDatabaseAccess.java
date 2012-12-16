package com.aurelia.loaning.db;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoanDatabaseAccess {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "loan.db";

	private final DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:MM:SS.SSS");

	private SQLiteDatabase db;

	private LoanDatabaseSQLite loanDb;

	public LoanDatabaseAccess(Context context) {
		// On créer la BDD et sa table
		loanDb = new LoanDatabaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open() {
		// on ouvre la BDD en écriture
		db = loanDb.getWritableDatabase();
	}

	public void close() {
		// on ferme l'accès à la BDD
		db.close();
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public long insert(Loan loan) {

		ContentValues values = new ContentValues();
		values.put("source", loan.getSource());
		values.put("destination", loan.getDestination());
		values.put("start_date", loan.getStarteDate().toString(formatter));
		values.put("end_date", loan.getEndDate().toString(formatter));
		values.put("description", loan.getDescription());
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
