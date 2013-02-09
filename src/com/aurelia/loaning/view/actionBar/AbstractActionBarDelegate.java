package com.aurelia.loaning.view.actionBar;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.loansoverview.StandardLoansOverviewActivity;

public abstract class AbstractActionBarDelegate {

	protected Map<Integer, ActionBarItem> actionBarDefinition;

	abstract public ActionBar setupActionBar(ActionBar actionBar, ActionBar.TabListener tabListener);

	// TODO : correct to have it here?
	protected void backToLoansOverview(Activity activity) {
		Intent intent = new Intent(activity, StandardLoansOverviewActivity.class);
		activity.startActivity(intent);
	}

	public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity) {
		ActionBarItem actionBarItem = (ActionBarItem) actionBarDefinition.get(tab.getPosition());
		actionBarItem.action(ft, activity);
	}
}
