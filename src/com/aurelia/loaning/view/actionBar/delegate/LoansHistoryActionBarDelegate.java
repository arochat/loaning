package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.TabsActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.FilterLoansAction;

public class LoansHistoryActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {

		actionBarDefinition = new HashMap<Integer, ActionBarItem>();
		actionBarDefinition.put(0, new ActionBarItem("FILTER", "FILTER", 0, false, new FilterLoansAction()));

		return new TabsActionBarBuilder(actionBarDefinition, tabListener, actionBar).build();
	}

	@Override
	public Menu setupActionBar(Menu menu) {
		actionBarDefinition = new HashMap<Integer, ActionBarItem>();
		actionBarDefinition.put(0, new ActionBarItem("FILTER", "FILTER", 0, false, new FilterLoansAction()));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();
	}
}