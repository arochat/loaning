package com.aurelia.loaning.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.Transaction;
import com.aurelia.loaning.domain.TransactionFactory;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;

public class AddLoanActivity extends BaseActivity {

	public final static String ME = "ME";

	private BroadcastReceiver dbFeedbackReceiver;
	private IntentFilter intentFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_loan_form);
	}

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

	// graphical elements handling ----------------------------------------

	@Override
	protected void setupActionBar() {
		final ActionBar ab = super.createActionBar();

		// set up tabs nav
		ab.addTab(ab.newTab().setText("Overview").setTabListener(this), 0, false);
		ab.addTab(ab.newTab().setText("Add").setTabListener(this), 1, true);

		// default to tab navigation
		showTabsNav();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		if ("Overview".equals(tab.getText())) {
			super.backToLoansOverview();
		}
	}

	// --------------------------------------------

	// TODO : validate that form is completely filled
	public void addLoan(View view) {

		Transaction transaction = TransactionFactory.createTransaction(getLoanFromUI());
		Bundle bundle = new Bundle();
		bundle.putSerializable(Event.SAVE_LOANING.name(), transaction);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(Event.SAVE_LOANING.name());
		intent.putExtras(bundle);
		startService(intent);
	}

	public LoanFromUI getLoanFromUI() {
		return new LoanFromUIBuilder(this).getLoanFromUI();
	}

	private class DbFeedbackReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Event.LOAN_MODIFIED.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, LoansOverviewActivity.class);
				startActivity(displayLoansIntent);
			}
		}
	}

}
