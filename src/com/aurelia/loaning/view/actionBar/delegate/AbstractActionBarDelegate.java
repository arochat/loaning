package com.aurelia.loaning.view.actionBar.delegate;

import java.util.Map;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.actionBar.ActionBarItem;

public abstract class AbstractActionBarDelegate {

	protected Map<Integer, ActionBarItem> actionBarDefinition;

	abstract public ActionBar setupActionBar(ActionBar actionBar, ActionBar.TabListener tabListener);

	public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity) {
		ActionBarItem actionBarItem = (ActionBarItem) actionBarDefinition.get(tab.getPosition());
		actionBarItem.action(ft, activity);
	}
}
