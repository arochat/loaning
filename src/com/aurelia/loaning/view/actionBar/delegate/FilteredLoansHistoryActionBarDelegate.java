package com.aurelia.loaning.view.actionBar.delegate;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;

public class FilteredLoansHistoryActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		return actionBar;
	}

	@Override
	public Menu setupActionBar(Menu menu) {
		return menu;
	}

}