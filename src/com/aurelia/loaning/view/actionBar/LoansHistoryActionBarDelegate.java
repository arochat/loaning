package com.aurelia.loaning.view.actionBar;

import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.dialog.FilterLoansDialogFragment;

public class LoansHistoryActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {
		actionBarDefinition = new HashMap<Integer, ActionBarItem>();

		actionBarDefinition.put(0, new ActionBarItem("FILTER", "FILTER", 0, false) {

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
