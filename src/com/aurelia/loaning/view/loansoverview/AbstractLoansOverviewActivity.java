/**
 * 
 */
package com.aurelia.loaning.view.loansoverview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
import com.aurelia.loaning.view.AddLoanActivity;
import com.aurelia.loaning.view.BaseActivity;
import com.aurelia.loaning.view.DisplayDetailActivity;

/**
 * @author aurelia
 * 
 */
public abstract class AbstractLoansOverviewActivity extends BaseActivity {

	private ListView loansListView;
	protected List<AbstractLoan> loans;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.loans_overview_activity);
	}

	@Override
	protected void onStart() {
		super.onStart();
		setLoansToDisplay();
	}

	// loans handling ----------------------------------------------------------

	public void addLoan() {
		Intent intent = new Intent(this, AddLoanActivity.class);
		startActivity(intent);
	}

	public abstract void setLoansToDisplay();

	public List<AbstractLoan> getLoans() {
		return loans;
	}

	public void deleteAllLoans(boolean deleteFromHistory) {
		updateAllLoans(Event.DELETE_ALL_FILTERED_LOANS.name(), deleteFromHistory);
	}

	public void settleAllLoans() {
		updateAllLoans(Event.SETTLE_ALL_FILTERED_LOANS.name(), false);
	}

	protected void setUpDisplay() {
		if (loans != null & !Collections.EMPTY_LIST.equals(loans)) {
			String[] loanString = new String[loans.size()];

			for (int i = 0; i < loans.size(); i++) {
				loanString[i] = loans.get(i).toString();
			}
			loansListView = (ListView) findViewById(android.R.id.list);
			loansListView.setAdapter(new LoansArrayAdapter(this, loans));
		} else {
			CharSequence text = "No loans found";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(this, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
	}

	protected void handleClickEvent(final String callingActivity) {
		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AbstractLoan loan = (AbstractLoan) parent.getItemAtPosition(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable(Event.DISPLAY_LOAN_DETAIL.name(), loan);
				bundle.putSerializable("CALLING_ACTIVITY", callingActivity);
				Intent intent = new Intent(AbstractLoansOverviewActivity.this, DisplayDetailActivity.class);
				intent.setAction(Event.DISPLAY_LOAN_DETAIL.name());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		};
		if (loansListView != null) {
			loansListView.setOnItemClickListener(listener);
		}
	}

	private void updateAllLoans(String action, boolean deleteFromHistory) {
		// need to pass an implementation to putSerializable
		ArrayList<Long> loanIds = new ArrayList<Long>();
		for (AbstractLoan loan : loans) {
			loanIds.add(loan.getId());
		}

		Bundle bundle = new Bundle();
		bundle.putSerializable(action, loanIds);
		bundle.putBoolean("delete_from_history", deleteFromHistory);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(action);
		intent.putExtras(bundle);
		startService(intent);
	}

}
