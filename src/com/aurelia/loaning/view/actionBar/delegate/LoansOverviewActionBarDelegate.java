package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.AddLoanAction;
import com.aurelia.loaning.view.actionBar.action.FilterLoansAction;

public class LoansOverviewActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public Menu setupActionBar(Menu menu) {

		actionBarDefinition = new HashMap<Integer, ActionBarItem>();
		actionBarDefinition.put(0, new ActionBarItem("ADD", "", R.drawable.icon_add, false, new AddLoanAction()));
		actionBarDefinition.put(1, new ActionBarItem("FILTER", "FILTER", 0, false, new FilterLoansAction()));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();
	}

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		// TODO Auto-generated method stub
		return null;
	}

}
