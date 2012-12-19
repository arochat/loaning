/**
 * 
 */
package com.aurelia.loaning.view;

import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.Transaction;
import com.aurelia.loaning.domain.TransactionContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanFetcher;

/**
 * @author aurelia
 * 
 */
public class LoansOverviewActivity extends ListActivity {

	private BroadcastReceiver loansReceiver;

	// private ArrayAdapter<Transaction> arrayAdapter;

	private ListView loansListView;

	// private List<Transaction> transactions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loans_overview_activity);
		// loansListView = (ListView) findViewById(R.id.list);
		// loansListView.setAdapter(new LoansArrayAdapter(this,
		// R.layout.loans_overview_activity));
	}

	@Override
	protected void onStart() {
		if (loansReceiver == null) {
			loansReceiver = new LoanReceiver();
			IntentFilter intentFilter = new IntentFilter(Event.SHOW_LOANINGS.name());
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

	public void addLoan(View view) {
		Intent intent = new Intent(this, AddLoanActivity.class);
		startActivity(intent);
	}

	private class LoanReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Event.SHOW_LOANINGS.name().equals(intent.getAction())) {
				TransactionContainer transactionContainer = (TransactionContainer) intent.getExtras().getSerializable(
						Event.SHOW_LOANINGS.name());

				if (transactionContainer != null) {
					List<Transaction> transactions = transactionContainer.getTransactions();

					if (transactions != null & !Collections.EMPTY_LIST.equals(transactions)) {
						String[] transactionString = new String[transactions.size()];

						for (int i = 0; i < transactions.size(); i++) {
							transactionString[i] = transactions.get(i).toString();
						}

						loansListView = (ListView) findViewById(android.R.id.list);
						loansListView.setAdapter(new LoansArrayAdapter(context, transactions));
					}
				}
			}
		}
	}
}
