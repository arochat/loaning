package com.aurelia.loaning.view.actionBar.delegate;

import java.util.Map;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.aurelia.loaning.view.actionBar.ActionBarItem;

public abstract class AbstractActionBarDelegate {

	protected Map<String, ActionBarItem> actionBarDefinition;

	abstract public Menu setupActionBar(Menu menu);

	public void handleActions(MenuItem menuItem, SherlockFragmentActivity activity) {
		ActionBarItem actionBarItem = (ActionBarItem) actionBarDefinition.get(menuItem.getTitle());
		actionBarItem.action(activity);
	}
}
