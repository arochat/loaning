package com.aurelia.loaning.view.actionBar.delegate;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;

public class AddLoanActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, ActionBar.TabListener tabListener) {
		return actionBar;
	}

	@Override
	public Menu setupActionBar(Menu menu) {
		return menu;
	}

}
