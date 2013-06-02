package com.aurelia.loaning.view.actionBar.action;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.dialog.AddLoanDialogFragment;

public class AddLoanCommand implements ActionBarCommand {

	@Override
	public void performAction(SherlockFragmentActivity activity) {
		// TODO : completely handle fragments
		// stack as in
		// http://www.edumobile.org/android/android-development/fragment-example-in-android/

		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently
		// showing
		// dialog, so make our own transaction and take care of that
		// here.
		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
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
