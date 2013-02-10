package com.aurelia.loaning.view.actionBar.delegate;

import java.util.HashMap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.ActionBarBuilder;
import com.aurelia.loaning.view.actionBar.ActionBarItem;
import com.aurelia.loaning.view.actionBar.action.CopyLoanAction;
import com.aurelia.loaning.view.actionBar.action.DeleteLoanAction;
import com.aurelia.loaning.view.actionBar.action.EditLoanAction;
import com.aurelia.loaning.view.actionBar.action.SettleLoanAction;

public class DisplayDetailActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {

		actionBarDefinition = new HashMap<Integer, ActionBarItem>();

		actionBarDefinition.put(0, new ActionBarItem("COPY", "", R.drawable.icon_copy, false, new CopyLoanAction()));
		actionBarDefinition.put(1, new ActionBarItem("EDIT", "", R.drawable.icon_edit, false, new EditLoanAction()));
		actionBarDefinition.put(2, new ActionBarItem("SETTLE", "", R.drawable.icon_settle, false,
				new SettleLoanAction()));
		actionBarDefinition.put(3, new ActionBarItem("DELETE", "", R.drawable.icon_delete, false,
				new DeleteLoanAction()));

		return new ActionBarBuilder(actionBarDefinition, tabListener, actionBar).build();
	}
}
