package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.CopyLoanCommand;
import com.aurelia.loaning.view.actionBar.action.EditLoanCommand;
import com.aurelia.loaning.view.actionBar.action.SettleLoanCommand;

public class DisplayDetailActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public Menu setupActionBar(Menu menu) {

		actionBarDefinition = new HashMap<String, ActionBarItem>();

		actionBarDefinition.put("COPY", new ActionBarItem("COPY", "COPY", R.drawable.icon_copy, false,
				new CopyLoanCommand()));
		actionBarDefinition.put("EDIT", new ActionBarItem("EDIT", "EDIT", R.drawable.icon_edit, false,
				new EditLoanCommand()));
		actionBarDefinition.put("SETTLE", new ActionBarItem("SETTLE", "SETTLE", R.drawable.icon_settle, false,
				new SettleLoanCommand()));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();

	}
}
