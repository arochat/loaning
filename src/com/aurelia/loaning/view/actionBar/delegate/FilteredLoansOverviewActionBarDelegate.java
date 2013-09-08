package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.ComputeBalanceCommand;
import com.aurelia.loaning.view.actionBar.action.SettleAllCommand;

public class FilteredLoansOverviewActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public Menu setupActionBar(Menu menu) {

		actionBarDefinition = new HashMap<String, ActionBarItem>();

		// actionBarDefinition.put("DELETE_ALL", new ActionBarItem("DELETE_ALL",
		// "DELETE_ALL", R.drawable.icon_delete,
		// false, new DeleteAllCommand()));

		actionBarDefinition.put("BALANCE", new ActionBarItem("COMPUTE_BALANCE", "BALANCE", R.drawable.icon_balance,
				false, new ComputeBalanceCommand()));
		actionBarDefinition.put("SETTLE_ALL", new ActionBarItem("SETTLE_ALL", "SETTLE_ALL", R.drawable.icon_settle,
				false, new SettleAllCommand()));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();
	}

}
