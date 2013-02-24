package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.CopyLoanCommand;
import com.aurelia.loaning.view.actionBar.action.EditLoanCommand;
import com.aurelia.loaning.view.actionBar.action.SettleLoanCommand;

public class DisplayDetailActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		//
		// actionBarDefinition = new HashMap<String, ActionBarItem>();
		//
		// actionBarDefinition.put(0, new ActionBarItem("COPY", "",
		// R.drawable.icon_copy, false, new CopyLoanAction()));
		// actionBarDefinition.put(1, new ActionBarItem("EDIT", "",
		// R.drawable.icon_edit, false, new EditLoanAction()));
		// actionBarDefinition.put(2, new ActionBarItem("SETTLE", "",
		// R.drawable.icon_settle, false,
		// new SettleLoanAction()));
		// actionBarDefinition.put(3, new ActionBarItem("DELETE", "",
		// R.drawable.icon_delete, false,
		// new DeleteLoanAction()));
		//
		// return new TabsActionBarBuilder(actionBarDefinition, tabListener,
		// actionBar).build();

		return null;
	}

	@Override
	public Menu setupActionBar(Menu menu) {

		actionBarDefinition = new HashMap<String, ActionBarItem>();

		actionBarDefinition.put("COPY", new ActionBarItem("COPY", "COPY", R.drawable.icon_copy, false,
				new CopyLoanCommand()));
		actionBarDefinition.put("EDIT", new ActionBarItem("EDIT", "EDIT", R.drawable.icon_edit, false,
				new EditLoanCommand()));
		actionBarDefinition.put("SETTLE", new ActionBarItem("SETTLE", "SETTLE", R.drawable.icon_settle, false,
				new SettleLoanCommand()));
		// actionBarDefinition.put("DELETE", new ActionBarItem("DELETE",
		// "DELETE", R.drawable.icon_delete, false,
		// new DeleteLoanCommand()));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();

	}
}
