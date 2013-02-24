package com.aurelia.loaning.view.actionBar.action;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public abstract class ActionBarCommandWithConfirmation implements ActionBarCommand {

	private String confirmationMessage;

	abstract public void performActionCallback(Activity activity);

	public void performActionWithDialogFragment(SherlockFragmentActivity activity, DialogFragment dialogFragment) {

		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		dialogFragment.show(ft, "dialog");
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}
}
