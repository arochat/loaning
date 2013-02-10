package com.aurelia.loaning.view.actionBar.action;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.DisplayDetailActivity;

public class SettleLoanAction implements ActionBarAction {

	@Override
	public void performAction(FragmentTransaction ft, SherlockFragmentActivity activity) {

		if (activity instanceof DisplayDetailActivity) {
			DisplayDetailActivity displayDetailActivity = (DisplayDetailActivity) activity;
			displayDetailActivity.settleLoan();
		}

	}

}
