package com.aurelia.loaning.view.actionBar.action;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public interface ActionBarAction {

	void performAction(FragmentTransaction ft, SherlockFragmentActivity activity);

}
