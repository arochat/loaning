package com.aurelia.loaning.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.Transaction;
import com.aurelia.loaning.domain.TransactionFactory;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;

public class AddLoanActivity extends Activity {

	public final static String ME = "ME";

	private BroadcastReceiver dbFeedbackReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (dbFeedbackReceiver == null) {
			dbFeedbackReceiver = new DbFeedbackReceiver();
			IntentFilter intentFilter = new IntentFilter(Event.LOAN_SAVED.name());
			registerReceiver(dbFeedbackReceiver, intentFilter);
		}
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// TODO : validate that form is completely filled
	public void addLoan(View view) {

		Transaction transaction = TransactionFactory.createTransaction(getLoanFromUI());
		Bundle bundle = new Bundle();
		bundle.putSerializable(Event.SAVE_LOANING.name(), transaction);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.putExtras(bundle);
		startService(intent);
	}

	public LoanFromUI getLoanFromUI() {
		return new LoanFromUIBuilder(this).getLoanFromUI();
	}

	private class DbFeedbackReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Event.LOAN_SAVED.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, LoansOverviewActivity.class);
				startActivity(displayLoansIntent);
			}
		}
	}

}
