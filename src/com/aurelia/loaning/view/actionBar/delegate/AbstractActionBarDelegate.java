package com.aurelia.loaning.view.actionBar.delegate;

import java.util.Map;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.aurelia.loaning.view.actionBar.ActionBarItem;

public abstract class AbstractActionBarDelegate {

	protected Map<Integer, ActionBarItem> actionBarDefinition;

	abstract public ActionBar setupActionBar(ActionBar actionBar, ActionBar.TabListener tabListener);

	abstract public Menu setupActionBar(Menu menu);

	public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity) {
		ActionBarItem actionBarItem = (ActionBarItem) actionBarDefinition.get(tab.getPosition());
		actionBarItem.action(/* ft, */activity);
	}

	public void handleActions(MenuItem menuItem, SherlockFragmentActivity activity) {
		ActionBarItem actionBarItem = (ActionBarItem) actionBarDefinition.get(menuItem.getItemId());
		actionBarItem.action(activity);
	}
}
