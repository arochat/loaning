package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.AddLoanCommand;
import com.aurelia.loaning.view.actionBar.action.FilterLoansCommand;

public class LoansOverviewActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public Menu setupActionBar(Menu menu) {

		actionBarDefinition = new HashMap<String, ActionBarItem>();
		actionBarDefinition.put("ADD",
				new ActionBarItem("ADD", "ADD", R.drawable.icon_add, false, new AddLoanCommand()));
		actionBarDefinition.put("FILTER", new ActionBarItem("FILTER", "FILTER", R.drawable.icon_filter, false,
				new FilterLoansCommand()));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();
	}
}
