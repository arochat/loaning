/**
 * 
 */
package com.aurelia.loaning.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanFetcher;

/**
 * @author aurelia
 * 
 */
public class LoansOverviewActivity extends Activity {

	private BroadcastReceiver loansReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loans_overview_activity);
	}

	@Override
	protected void onStart() {
		if (loansReceiver == null) {
			loansReceiver = new LoanReceiver();
			IntentFilter intentFilter = new IntentFilter(
					Event.SHOW_LOANINGS.name());
			registerReceiver(loansReceiver, intentFilter);
		}
		Intent intent = new Intent(this, LoanFetcher.class);
		startService(intent);
		super.onStart();
	}

	@Override
	protected void onStop() {
		unregisterReceiver(loansReceiver);
		super.onStop();
	}

	private class LoanReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				Bundle loans = intent.getExtras();
				TextView name = (TextView) findViewById(R.id.textView2);
				name.setText(loans.getCharSequence(Event.SHOW_LOANINGS.name()));
			}
		}
	}
}
