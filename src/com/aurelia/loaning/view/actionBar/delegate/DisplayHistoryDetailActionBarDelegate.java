package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.DeleteLoanCommand;

public class DisplayHistoryDetailActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public Menu setupActionBar(Menu menu) {

		actionBarDefinition = new HashMap<String, ActionBarItem>();

		actionBarDefinition.put("DELETE", new ActionBarItem("DELETE", "DELETE", R.drawable.icon_delete, false,
				new DeleteLoanCommand()));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();

	}
}
