package com.aurelia.loaning.service;

import android.app.IntentService;
import android.content.Intent;

public class LoanSaver extends IntentService {

	public LoanSaver() {
		super(LoanSaver.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		// TODO display toast message once loan saved
		intent.toString();
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
