package com.aurelia.loaning.service;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.aurelia.loaning.db.Loan;
import com.aurelia.loaning.db.LoanDatabaseAccess;
import com.aurelia.loaning.domain.DomainToEntityConverter;
import com.aurelia.loaning.domain.Transaction;
import com.aurelia.loaning.event.Event;

public class LoanSaver extends IntentService {

	private LoanDatabaseAccess databaseAccess;
	private DomainToEntityConverter converter;

	public LoanSaver() {
		super(LoanSaver.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {

		// LoanTransaction received in intent
		Bundle loans = intent.getExtras();
		Transaction transaction = (Transaction) loans.getSerializable(Event.SAVE_LOANING.name());

		if (databaseAccess == null) {
			databaseAccess = new LoanDatabaseAccess(this);
		}
		if (converter == null) {
			converter = new DomainToEntityConverter();
		}
		databaseAccess.open();
		databaseAccess.insert(converter.convert(transaction));

		// TODO display toast message once loan saved
		List<Loan> myLoans = databaseAccess.read();
		myLoans.get(0);

		databaseAccess.close();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
		// TODO : what do we need to return here?
	}

}
