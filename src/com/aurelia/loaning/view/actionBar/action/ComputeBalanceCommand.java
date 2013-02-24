package com.aurelia.loaning.view.actionBar.action;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.dialog.BalanceResultDialogFragment;

public class ComputeBalanceCommand implements ActionBarCommand {

	@Override
	public void performAction(/* FragmentTransaction ft, */SherlockFragmentActivity activity) {

		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		BalanceResultDialogFragment balanceResultDialogFragment = new BalanceResultDialogFragment(activity);
		balanceResultDialogFragment.show(ft, "dialog");

	}
}
