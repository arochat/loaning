package com.aurelia.loaning.service;

import android.app.IntentService;
import android.content.Intent;

import com.aurelia.loaning.db.LoanDatabaseAccess;
import com.aurelia.loaning.event.Event;

public class LoanRemover extends IntentService {

	private LoanDatabaseAccess databaseAccess;

	public LoanRemover() {
		super(LoanSaver.class.getName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		if (databaseAccess == null) {
			databaseAccess = new LoanDatabaseAccess(this);
			databaseAccess.open();
		}
		databaseAccess.removeAll();
		databaseAccess.close();

		sendBroadcast(new Intent(Event.SHOW_LOANINGS.name()));

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
