package com.aurelia.loaning.view.actionBar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.dialog.AddLoanDialogFragment;

public class LoansOverviewActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		actionBar.addTab(actionBar.newTab().setText("Overview").setTabListener(tabListener), 0, true);
		actionBar.addTab(actionBar.newTab().setText("Add").setTabListener(tabListener), 1, false);
		actionBar.addTab(actionBar.newTab().setText("Filter").setTabListener(tabListener), 2, false);
		return actionBar;
	}

	@Override
	public void handleActions(Tab tab, FragmentTransaction ft, SherlockFragmentActivity activity) {
		if ("Add".equals(tab.getText())) {

			// TODO : completely handle fragments
			// stack as in
			// http://www.edumobile.org/android/android-development/fragment-example-in-android/

			// DialogFragment.show() will take care of adding the fragment
			// in a transaction. We also want to remove any currently showing
			// dialog, so make our own transaction and take care of that here.
			ft = activity.getSupportFragmentManager().beginTransaction();
			Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);

			// Create and show the dialog.
			AddLoanDialogFragment addLoanDialogFragment = new AddLoanDialogFragment(activity);
			addLoanDialogFragment.show(ft, "dialog");
		}

	}

}
