package com.aurelia.loaning.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoanFactory;
import com.aurelia.loaning.domain.LoanType;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
import com.aurelia.loaning.view.actionBar.AddLoanActionBarDelegate;
import com.aurelia.loaning.view.dialog.AddLoanDialogFragment;
import com.aurelia.loaning.view.loansoverview.StandardLoansOverviewActivity;

public class AddLoanActivity extends BaseActivity {

	public final static String ME = "ME";

	private BroadcastReceiver dbFeedbackReceiver;
	private IntentFilter intentFilter;
	private LoanType loanType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setActionBarDelegate(new AddLoanActionBarDelegate());
		super.onCreate(savedInstanceState);

		loanType = (LoanType) getIntent().getExtras().get(AddLoanDialogFragment.LOAN_TYPE);

		switch (loanType) {
		case MONEY_LOAN:
			// fallthrough
		case MONEY_BORROWING:
			setContentView(R.layout.add_money_loan_form);
			break;
		case OBJECT_LOAN:
			// fallthrough
		case OBJECT_BORROWING:
			setContentView(R.layout.add_object_loan_form);
			break;
		default:
		}
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

	// --------------------------------------------

	public LoanType getLoanType() {
		return loanType;
	}

	// TODO : validate that form is completely filled
	public void addLoan(View view) {

		AbstractLoan loan = LoanFactory.createLoan(getLoanFromUI());
		Bundle bundle = new Bundle();
		bundle.putSerializable(Event.SAVE_LOANING.name(), loan);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(Event.SAVE_LOANING.name());
		intent.putExtras(bundle);
		startService(intent);
	}

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
