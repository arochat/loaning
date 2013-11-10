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
import android.os.Handler;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanFetcher;
import com.aurelia.loaning.service.notification.NotificationChecker;
import com.aurelia.loaning.service.notification.NotificationCheckerService;
import com.aurelia.loaning.view.AddLoanActivity;
import com.aurelia.loaning.view.actionBar.delegate.LoansOverviewActionBarDelegate;

/**
 * @author aurelia
 * 
 */
public class StandardLoansOverviewActivity extends AbstractLoansOverviewActivity {

	private int interval = 1000 * 60 * 60 * 24; //
	private Handler handler;
	private BroadcastReceiver loansReceiver;
	private IntentFilter intentFilter;
	private NotificationChecker notificationChecker;

	Runnable mStatusChecker = new Runnable() {
		@Override
		public void run() {
			notificationChecker.fireNotification(StandardLoansOverviewActivity.super.getApplicationContext()); // this function can change value of mInterval.
			handler.postDelayed(mStatusChecker, interval);
		}
	};

	@Override
	protected void onCreate(Bundle bundle) {
		this.setActionBarDelegate(new LoansOverviewActionBarDelegate());
		super.onCreate(bundle);

		if (handler == null) {
			handler = new Handler();
		}
		if (notificationChecker == null) {
			notificationChecker = new NotificationChecker();
		}
		// on startup, check for elapsed loans to notify
		startService(new Intent(this, NotificationCheckerService.class));
		startRepeatingTask();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (loansReceiver == null) {
			loansReceiver = new LoanReceiver();
			intentFilter = new IntentFilter(Event.SHOW_LOANINGS.name());
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
		startService(intent);
	}

	public void addLoan() {
		Intent intent = new Intent(this, AddLoanActivity.class);
		startActivity(intent);
	}

	// -----------------------------------------------------------

	private class LoanReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			List<AbstractLoan> loans = new ArrayList<AbstractLoan>();
			if (intent != null && Event.SHOW_LOANINGS.name().equals(intent.getAction())) {
				if (intent.getExtras() != null) {

					LoansContainer loansContainer = (LoansContainer) intent.getExtras().getSerializable(
							Event.SHOW_LOANINGS.name());

					if (loansContainer != null) {
						loans = loansContainer.getLoans();
						StandardLoansOverviewActivity.super.loans = loans;

						setUpDisplay();
						handleClickEvent(StandardLoansOverviewActivity.this.getClass().getName());
					}
				}
			}
		}
	}

	// -------------------------------------------------------

	void startRepeatingTask() {
		mStatusChecker.run();
	}

	void stopRepeatingTask() {
		handler.removeCallbacks(mStatusChecker);
	}

}
