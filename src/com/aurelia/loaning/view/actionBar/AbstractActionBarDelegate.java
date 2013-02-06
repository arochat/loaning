package com.aurelia.loaning.view.actionBar;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.loansoverview.AbstractLoansOverviewActivity;

public abstract class AbstractActionBarDelegate {

	abstract public ActionBar setupActionBar(ActionBar actionBar, ActionBar.TabListener tabListener);

	abstract public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity);

	// TODO : correct to have it here?
	protected void backToLoansOverview(Activity activity) {
		Intent intent = new Intent(activity, AbstractLoansOverviewActivity.class);
		activity.startActivity(intent);
	}
}
