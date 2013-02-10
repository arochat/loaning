package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarBuilder;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.action.AddLoanAction;
import com.aurelia.loaning.view.actionBar.action.ComputeBalanceAction;
import com.aurelia.loaning.view.actionBar.action.DeleteAllAction;
import com.aurelia.loaning.view.actionBar.action.SettleAllAction;

public class FilteredLoansOverviewActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {

		actionBarDefinition = new HashMap<Integer, ActionBarItem>();

		actionBarDefinition.put(0, new ActionBarItem("ADD", "", R.drawable.icon_add, false, new AddLoanAction()));
		actionBarDefinition.put(1,
				new ActionBarItem("COMPUTE_BALANCE", "BALANCE", 0, false, new ComputeBalanceAction()));
		actionBarDefinition.put(2, new ActionBarItem("SETTLE_ALL", "", R.drawable.icon_settle, false,
				new SettleAllAction()));
		actionBarDefinition.put(3, new ActionBarItem("DELETE_ALL", "", R.drawable.icon_delete, false,
				new DeleteAllAction()));

		return new ActionBarBuilder(actionBarDefinition, tabListener, actionBar).build();

	}

}
