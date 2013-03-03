package com.aurelia.loaning.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.aurelia.loaning.domain.LoanType;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.view.loansoverview.StandardLoansOverviewActivity;

public abstract class LoanFormActivity extends BaseActivity {

	public final static String ME = "ME";

	private BroadcastReceiver dbFeedbackReceiver;
	private IntentFilter intentFilter;
	protected LoanType loanType;

	@Override
	protected void onResume() {
		super.onResume();
		if (dbFeedbackReceiver == null) {
			dbFeedbackReceiver = new DbFeedbackReceiver();
			intentFilter = new IntentFilter(Event.LOAN_MODIFIED.name());
		}
		registerReceiver(dbFeedbackReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(dbFeedbackReceiver);
	}

	// --------------------------------------------

	public LoanType getLoanType() {
		return loanType;
	}

	public abstract void saveLoan(View view);

	public AbstractLoanFromUI getLoanFromUI() {
		return new LoanFromUIBuilder(this).getLoanFromUI();
	}

	private class DbFeedbackReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Event.LOAN_MODIFIED.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, StandardLoansOverviewActivity.class);
				startActivity(displayLoansIntent);
			}
		}
	}
}
