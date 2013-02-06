package com.aurelia.loaning.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aurelia.loaning.view.loansoverview.AbstractLoansOverviewActivity;

public class DbFeedbackReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null && Event.LOAN_MODIFIED.name().equals(intent.getAction())) {
			Intent displayLoansIntent = new Intent(context, AbstractLoansOverviewActivity.class);
			// startActivity(displayLoansIntent);
		}
	}
}
