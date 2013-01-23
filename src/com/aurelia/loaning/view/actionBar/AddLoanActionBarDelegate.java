package com.aurelia.loaning.view.actionBar;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class AddLoanActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, ActionBar.TabListener tabListener) {

		actionBar.addTab(actionBar.newTab().setText("Overview").setTabListener(tabListener), 0, false);
		actionBar.addTab(actionBar.newTab().setText("Add").setTabListener(tabListener), 1, true);

		return actionBar;
	}

	@Override
	public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity) {

		if ("Overview".equals(tab.getText())) {
			super.backToLoansOverview(activity);
		}

	}

}
