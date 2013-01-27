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
import com.aurelia.loaning.view.actionBar.LoansOverviewActionBarDelegate;

/**
 * @author aurelia
 * 
 */
public class FilteredLoansOverviewActivity extends BaseActivity {

	private String filter;
	private ListView loansListView;
	private List<AbstractLoan> loans;

	@Override
	protected void onCreate(Bundle bundle) {
		this.setActionBarDelegate(new LoansOverviewActionBarDelegate());
		super.onCreate(bundle);
		LoansContainer loansContainer = (LoansContainer) getIntent().getExtras().getSerializable("loans");
		loans = loansContainer.getLoans();
		setContentView(R.layout.loans_overview_activity);
	}

	@Override
	protected void onStart() {
		super.onStart();
		loansListView = (ListView) findViewById(android.R.id.list);
		loansListView.setAdapter(new LoansArrayAdapter(this, loans));
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

	public void computeBalance() {
		// TODO
		// Intent intent = new Intent(this, AddLoanActivity.class);
		// startActivity(intent);
	}

}
