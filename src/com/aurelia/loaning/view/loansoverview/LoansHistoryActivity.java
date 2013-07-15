/**
 * 
 */
package com.aurelia.loaning.view.loansoverview;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.AbstractLoan.LoanStatus;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanFetcher;
import com.aurelia.loaning.view.actionBar.delegate.LoansHistoryActionBarDelegate;

/**
 * @author aurelia
 * 
 */
public class LoansHistoryActivity extends AbstractLoansOverviewActivity {

	private BroadcastReceiver loansReceiver;
	private IntentFilter intentFilter;

	@Override
	protected void onCreate(Bundle bundle) {
		this.setActionBarDelegate(new LoansHistoryActionBarDelegate());
		super.onCreate(bundle);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (loansReceiver == null) {
			loansReceiver = new LoanReceiver();
			intentFilter = new IntentFilter(Event.SHOW_LOANINGS_HISTORY.name());
		}
		registerReceiver(loansReceiver, intentFilter);
		setLoansToDisplay();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(loansReceiver);
		super.onPause();
	}

	// loans handling ----------------------------------------------------------

	@Override
	public void setLoansToDisplay() {
		Intent intent = new Intent(this, LoanFetcher.class);
		intent.putExtra("status", LoanStatus.SETTLED.name());
		startService(intent);
	}

	// -----------------------------------------------------------

	private class LoanReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			List<AbstractLoan> loans = new ArrayList<AbstractLoan>();
			if (intent != null && Event.SHOW_LOANINGS_HISTORY.name().equals(intent.getAction())) {
				if (intent.getExtras() != null) {

					LoansContainer loansContainer = (LoansContainer) intent.getExtras().getSerializable(
							Event.SHOW_LOANINGS_HISTORY.name());

					if (loansContainer != null) {
						loans = loansContainer.getLoans();
						LoansHistoryActivity.super.loans = loans;

						setUpDisplay();
						handleClickEvent(LoansHistoryActivity.this.getClass().getName());
					}
				} else {
					Intent displayLoansIntent = new Intent(context, LoansHistoryActivity.class);
					startActivity(displayLoansIntent);
				}
			}
		}
	}

}
