package com.aurelia.loaning.view.actionBar;

import java.util.HashMap;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.DisplayDetailActivity;

public class DisplayDetailActionBarDelegate extends AbstractActionBarDelegate {

	@Override
	public ActionBar setupActionBar(ActionBar actionBar, TabListener tabListener) {

		actionBarDefinition = new HashMap<Integer, ActionBarItem>();

		actionBarDefinition.put(0, new ActionBarItem("COPY", "", R.drawable.icon_copy, false) {

			@Override
			public void action(FragmentTransaction ft, SherlockFragmentActivity activity) {
				// TODO Auto-generated method stub

			}
		});

		actionBarDefinition.put(1, new ActionBarItem("EDIT", "", R.drawable.icon_edit, false) {

			@Override
			public void action(FragmentTransaction ft, SherlockFragmentActivity activity) {
				// TODO Auto-generated method stub

			}
		});

		actionBarDefinition.put(2, new ActionBarItem("SETTLE", "", R.drawable.icon_settle, false) {

			@Override
			public void action(FragmentTransaction ft, SherlockFragmentActivity activity) {
				if (activity instanceof DisplayDetailActivity) {
					DisplayDetailActivity displayDetailActivity = (DisplayDetailActivity) activity;
					displayDetailActivity.settleLoan();
				}
			}
		});

		actionBarDefinition.put(3, new ActionBarItem("DELETE", "", R.drawable.icon_delete, false) {

			@Override
			public void action(FragmentTransaction ft, SherlockFragmentActivity activity) {
				if (activity instanceof DisplayDetailActivity) {
					DisplayDetailActivity displayDetailActivity = (DisplayDetailActivity) activity;
					displayDetailActivity.deleteLoan();
				}

			}
		});

		return new ActionBarBuilder(actionBarDefinition, tabListener, actionBar).build();
	}
}
