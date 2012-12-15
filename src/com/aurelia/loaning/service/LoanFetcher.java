package com.aurelia.loaning.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.aurelia.loaning.event.Event;

public class LoanFetcher extends IntentService {

	public LoanFetcher() {
		super(LoanFetcher.class.getSimpleName());
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
		// TODO : what do we need to return here?
	}

	@Override
	protected void onHandleIntent(final Intent intentReceived) {
		Bundle result = new Bundle();
		result.putString(Event.SHOW_LOANINGS.name(),
				"the result found, youpi, patate!!!");
		Intent feedback = new Intent(Event.SHOW_LOANINGS.name());
		feedback.putExtras(result);
		sendBroadcast(feedback);
	}
}
