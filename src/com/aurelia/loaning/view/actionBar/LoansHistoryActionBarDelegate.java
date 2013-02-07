package com.aurelia.loaning.view.actionBar;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class LoansHistoryActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		actionBar.addTab(actionBar.newTab().setText("History").setTabListener(tabListener), 0, true);
		actionBar.addTab(actionBar.newTab().setText("Overview").setTabListener(tabListener), 1, false);
		return actionBar;
	}

	@Override
	public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity) {
		if ("Overview".equals(tab.getText())) {
			super.backToLoansOverview(activity);
		}
	}

}
