package com.aurelia.loaning.view.actionBar.action;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.dialog.FilterLoansDialogFragment;

public class FilterLoansCommand implements ActionBarCommand {

	@Override
	public void performAction(SherlockFragmentActivity activity) {
		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		FilterLoansDialogFragment filterLoansDialogFragment = new FilterLoansDialogFragment(activity);
		filterLoansDialogFragment.show(ft, "dialog");

	}

}
