/**
 * 
 */
package com.aurelia.loaning.view.loansoverview;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
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
		setUpDisplay();
		handleClickEvent(callingActivity);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	// loans handling ----------------------------------------------------------

	@Override
	public void setLoansToDisplay() {

		LoansContainer loansContainer = (LoansContainer) getIntent().getExtras().getSerializable("loans");
		allLoans = loansContainer.getLoans();
		char[] filter = (char[]) getIntent().getExtras().getSerializable("filter");
		filterString = new String(filter);
		loans = filterLoans(filterString);
	}

	public void settleAllLoans() {
		Bundle bundle = new Bundle();
		ArrayList<AbstractLoan> parcelableList = new ArrayList<AbstractLoan>();
		parcelableList.addAll(this.loans);
		bundle.putParcelableArrayList(Event.SETTLE_ALL_FILTERED_LOANS.name(), parcelableList);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(Event.SETTLE_ALL_FILTERED_LOANS.name());
		intent.putExtras(bundle);
		startService(intent);
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

}
