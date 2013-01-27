/**
 * 
 */
package com.aurelia.loaning.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanFetcher;
import com.aurelia.loaning.service.LoanRemover;
import com.aurelia.loaning.view.actionBar.LoansOverviewActionBarDelegate;

/**
 * @author aurelia
 * 
 */
public class LoansOverviewActivity extends BaseActivity {

	private BroadcastReceiver loansReceiver;
	private IntentFilter intentFilter;
	private ListView loansListView;
	private List<AbstractLoan> loans;

	@Override
	protected void onCreate(Bundle bundle) {
		this.setActionBarDelegate(new LoansOverviewActionBarDelegate());
		super.onCreate(bundle);
		setContentView(R.layout.loans_overview_activity);
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadLoansOverview();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (loansReceiver == null) {
			loansReceiver = new LoanReceiver();
			intentFilter = new IntentFilter(Event.SHOW_LOANINGS.name());
		}
		registerReceiver(loansReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		unregisterReceiver(loansReceiver);
		super.onPause();
	}

	// loans handling ----------------------------------------------------------

	public void addLoan() {
		Intent intent = new Intent(this, AddLoanActivity.class);
		startActivity(intent);
	}

	public void removeAll() {
		Intent intent = new Intent(this, LoanRemover.class);
		startService(intent);
	}

	public void loadLoansOverview() {
		Intent intent = new Intent(this, LoanFetcher.class);
		startService(intent);
	}

	public void filterLoans() {
		Intent intent = new Intent(this, FilteredLoansOverviewActivity.class);
		Bundle bundle = new Bundle();
		LoansContainer loansContainer = new LoansContainer();
		loansContainer.setLoans(loans);
		bundle.putSerializable("loans", loansContainer);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public List<AbstractLoan> getLoans() {
		return loans;
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
						LoansOverviewActivity.this.loans = loans;

						if (loans != null & !Collections.EMPTY_LIST.equals(loans)) {
							String[] loanString = new String[loans.size()];

							for (int i = 0; i < loans.size(); i++) {
								loanString[i] = loans.get(i).toString();
							}

						}
					}
				}
			}
			loansListView = (ListView) findViewById(android.R.id.list);
			loansListView.setAdapter(new LoansArrayAdapter(context, loans));

			OnItemClickListener listener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					AbstractLoan loan = (AbstractLoan) parent.getItemAtPosition(position);
					Bundle bundle = new Bundle();
					bundle.putSerializable(Event.DISPLAY_LOAN_DETAIL.name(), loan);
					Intent intent = new Intent(LoansOverviewActivity.this, DisplayDetailActivity.class);
					intent.setAction(Event.DISPLAY_LOAN_DETAIL.name());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			};
			loansListView.setOnItemClickListener(listener);
		}
	}

}
