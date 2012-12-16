/**
 * 
 */
package com.aurelia.loaning.view;

import java.util.List;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loans_overview_activity);
	}

	@Override
	protected void onStart() {
		// arrayAdapter = new LoansArrayAdapter(this,
		// R.layout.loans_overview_activity);
		// setListAdapter(arrayAdapter);

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

	private class LoanReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				TransactionContainer transactionContainer = (TransactionContainer) intent.getExtras().getSerializable(
						Event.SHOW_LOANINGS.name());

				List<Transaction> transactions = transactionContainer.getTransactions();
				String[] transactionString = new String[transactions.size()];

				for (int i = 0; i < transactions.size(); i++) {
					transactionString[i] = transactions.get(i).toString();
				}

				ListView listView = (ListView) findViewById(android.R.id.list);
				listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
						transactionString));

				// for (Transaction transaction :
				// transactionContainer.getTransactions()) {
				// arrayAdapter.add(transaction);
				// }
			}
		}
	}
}
