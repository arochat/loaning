/**
 * 
 */
package com.aurelia.loaning.view;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.view.actionBar.FilteredLoansOverviewActionBarDelegate;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author aurelia
 * 
 */
public class FilteredLoansOverviewActivity extends BaseActivity {

	// TODO : make rows clickable, and allow to see the detail as in
	// LoansOverviewActivity

	private String filterString;
	private ListView loansListView;
	private List<AbstractLoan> loans;
	private List<AbstractLoan> filteredLoans;

	@Override
	protected void onCreate(Bundle bundle) {
		this.setActionBarDelegate(new FilteredLoansOverviewActionBarDelegate());
		super.onCreate(bundle);
		LoansContainer loansContainer = (LoansContainer) getIntent().getExtras().getSerializable("loans");
		loans = loansContainer.getLoans();
		char[] filter = (char[]) getIntent().getExtras().getSerializable("filter");
		filterString = new String(filter);
		filteredLoans = filterLoans(filterString);
		setContentView(R.layout.loans_overview_activity);
	}

	@Override
	protected void onStart() {
		super.onStart();
		loansListView = (ListView) findViewById(android.R.id.list);
		loansListView.setAdapter(new LoansArrayAdapter(this, filteredLoans));
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	// loans handling ----------------------------------------------------------

	private List<AbstractLoan> filterLoans(final String filter) {

		Predicate<AbstractLoan> shouldBeDisplayed = new Predicate<AbstractLoan>() {
			@Override
			public boolean apply(AbstractLoan loan) {
				return loan.getPerson().toUpperCase().contains(filter.toUpperCase().trim());
			}
		};

		return Lists.newArrayList(Iterables.filter(this.loans, shouldBeDisplayed));
	}

	public List<AbstractLoan> getFilteredLoans() {
		return filteredLoans;
	}

	public String getFilterString() {
		return filterString;
	}

}
