package com.aurelia.loaning.view.actionBar;

import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.dialog.AddLoanDialogFragment;
import com.aurelia.loaning.view.dialog.FilterLoansDialogFragment;

public class LoansOverviewActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		actionBarDefinition = new HashMap<Integer, ActionBarItem>();

		actionBarDefinition.put(0, new ActionBarItem("ADD", "", R.drawable.icon_add, false) {

			@Override
			public void action(FragmentTransaction ft, SherlockFragmentActivity activity) {
				// TODO : completely handle fragments
				// stack as in
				// http://www.edumobile.org/android/android-development/fragment-example-in-android/

				// DialogFragment.show() will take care of adding the fragment
				// in a transaction. We also want to remove any currently
				// showing
				// dialog, so make our own transaction and take care of that
				// here.
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
		});

		actionBarDefinition.put(1, new ActionBarItem("FILTER", "FILTER", 0, false) {

			@Override
			public void action(FragmentTransaction ft, SherlockFragmentActivity activity) {
				ft = activity.getSupportFragmentManager().beginTransaction();
				Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
				if (prev != null) {
					ft.remove(prev);
				}
				ft.addToBackStack(null);

				// Create and show the dialog.
				FilterLoansDialogFragment filterLoansDialogFragment = new FilterLoansDialogFragment(activity);
				filterLoansDialogFragment.show(ft, "dialog");

			}
		});

		return new ActionBarBuilder(actionBarDefinition, tabListener, actionBar).build();
	}

}
