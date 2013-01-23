package com.aurelia.loaning.view.actionBar;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class DisplayDetailActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		actionBar.addTab(actionBar.newTab().setText("View").setTabListener(tabListener), 0, true);
		actionBar.addTab(actionBar.newTab().setText("Copy").setTabListener(tabListener), 1, false);
		actionBar.addTab(actionBar.newTab().setText("Edit").setTabListener(tabListener), 2, false);
		return actionBar;
	}

	@Override
	public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity) {
		if ("Overview".equals(tab.getText())) {
			super.backToLoansOverview(activity);
		}
	}

}
