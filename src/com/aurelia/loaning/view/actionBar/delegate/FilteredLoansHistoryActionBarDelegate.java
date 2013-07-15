package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.view.Menu;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.OptionsMenuActionBarBuilder;
import com.aurelia.loaning.view.actionBar.action.DeleteAllCommand;

public class FilteredLoansHistoryActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public Menu setupActionBar(Menu menu) {
		actionBarDefinition = new HashMap<String, ActionBarItem>();

		actionBarDefinition.put("DELETE_ALL", new ActionBarItem("DELETE_ALL", "DELETE_ALL", R.drawable.icon_delete,
				false, new DeleteAllCommand(true)));

		return new OptionsMenuActionBarBuilder().withActionBarDefinition(actionBarDefinition).withMenu(menu).build();
	}

}
