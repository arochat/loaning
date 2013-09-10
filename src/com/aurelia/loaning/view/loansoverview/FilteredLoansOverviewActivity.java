/**
 * 
 */
package com.aurelia.loaning.view.loansoverview;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.view.actionBar.delegate.FilteredLoansHistoryActionBarDelegate;
import com.aurelia.loaning.view.actionBar.delegate.FilteredLoansOverviewActionBarDelegate;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author aurelia
 * 
 */
public class FilteredLoansOverviewActivity extends AbstractLoansOverviewActivity {

	private String filterString;
	private List<AbstractLoan> allLoans;
	private String callingActivity;
	private BroadcastReceiver dbFeedbackReceiver;
	private IntentFilter intentFilter;
	private IntentFilter intentFilterForHistory;

	@Override
	protected void onCreate(Bundle bundle) {

		callingActivity = (String) getIntent().getExtras().getSerializable("callingActivity");

		if (LoansHistoryActivity.class.getName().equals(callingActivity)) {
			this.setActionBarDelegate(new FilteredLoansHistoryActionBarDelegate());
		} else {
			this.setActionBarDelegate(new FilteredLoansOverviewActionBarDelegate());
		}

		super.onCreate(bundle);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (dbFeedbackReceiver == null) {
			dbFeedbackReceiver = new DbFeedbackReceiver();
			intentFilter = new IntentFilter(Event.LOAN_MODIFIED.name());
			intentFilterForHistory = new IntentFilter(Event.DELETE_LOAN.name());
		}
		registerReceiver(dbFeedbackReceiver, intentFilter);
		registerReceiver(dbFeedbackReceiver, intentFilterForHistory);

		setUpDisplay();

		handleClickEvent(callingActivity);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(dbFeedbackReceiver);
	}

	// loans handling ----------------------------------------------------------

	@Override
	public void setLoansToDisplay() {

		LoansContainer loansContainer = (LoansContainer) getIntent().getExtras().getSerializable("loans");
		allLoans = loansContainer.getLoans();
		char[] filter = (char[]) getIntent().getExtras().getSerializable("filter");
		filterString = new String(filter);
		loans = filterLoans(filterString);

		TextView title = (TextView) findViewById(R.id.loan_list_titlee);

		if (LoansHistoryActivity.class.getName().equals(callingActivity)) {

			title.setText("History of my loans with " + filterString);
		} else {
			title.setText(title.getText() + " with " + filterString);
		}

	}

	private List<AbstractLoan> filterLoans(final String filter) {

		Predicate<AbstractLoan> shouldBeDisplayed = new Predicate<AbstractLoan>() {
			@Override
			public boolean apply(AbstractLoan loan) {
				return loan.getPerson().toUpperCase().contains(filter.toUpperCase().trim());
			}
		};

		return Lists.newArrayList(Iterables.filter(this.allLoans, shouldBeDisplayed));
	}

	public String getFilterString() {
		return filterString;
	}

	// ----------------------------------------------------------

	private class DbFeedbackReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Event.LOAN_MODIFIED.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, StandardLoansOverviewActivity.class);
				startActivity(displayLoansIntent);
			} else if (intent != null && Event.DELETE_LOAN.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, LoansHistoryActivity.class);
				startActivity(displayLoansIntent);
			}
		}
	}

}
