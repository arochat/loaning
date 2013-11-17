/**
 * 
 */
package com.aurelia.loaning.view.loansoverview;

import android.os.Bundle;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.view.actionBar.delegate.NoActionsActionBarDelegate;

/**
 * @author aurelia
 * 
 */
public class ElapsedLoansOverviewActivity extends AbstractLoansOverviewActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpDisplay(new NoActionsActionBarDelegate());
		handleClickEvent("elapsedLoansNotification");
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	// loans handling ----------------------------------------------------------

	@Override
	public void setLoansToDisplay() {

		LoansContainer loansContainer = (LoansContainer) getIntent().getExtras().getSerializable(
				Event.LIST_ELAPSED_LOANS.name());
		loans = loansContainer.getLoans();
		TextView title = (TextView) findViewById(R.id.loan_list_titlee);
		title.setText("My elapsed loans : ");

	}
}
