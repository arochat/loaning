package com.aurelia.loaning.view.actionBar.action;

import android.app.Activity;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.DisplayDetailActivity;
import com.aurelia.loaning.view.dialog.ConfirmationDialogFragment;

public class DeleteLoanCommand extends ActionBarCommandWithConfirmation {

	private boolean deleteFromHistory = false;

	public DeleteLoanCommand(boolean deleteFromHistory) {
		this.deleteFromHistory = deleteFromHistory;
	}

	@Override
	public void performAction(SherlockFragmentActivity activity) {
		super.setConfirmationMessage(activity.getResources().getString(R.string.confirmation_delete));
		super.performActionWithDialogFragment(activity, new ConfirmationDialogFragment(activity, this));
	}

	@Override
	public void performActionCallback(Activity activity) {
		if (activity instanceof DisplayDetailActivity) {
			DisplayDetailActivity displayDetailActivity = (DisplayDetailActivity) activity;
			displayDetailActivity.deleteLoan(deleteFromHistory);
		}
	}
}
