package com.aurelia.loaning.view.actionBar.action;

import android.app.Activity;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.dialog.ConfirmationDialogFragment;
import com.aurelia.loaning.view.loansoverview.AbstractLoansOverviewActivity;

public class DeleteAllCommand extends ActionBarCommandWithConfirmation {

	private boolean deleteFromHistory = false;

	public DeleteAllCommand(boolean deleteFromHistory) {
		this.deleteFromHistory = deleteFromHistory;
	}

	@Override
	public void performAction(SherlockFragmentActivity activity) {
		super.setConfirmationMessage(activity.getResources().getString(R.string.confirmation_delete_all));
		super.performActionWithDialogFragment(activity, new ConfirmationDialogFragment(activity, this));
	}

	@Override
	public void performActionCallback(Activity activity) {
		if (activity instanceof AbstractLoansOverviewActivity) {
			AbstractLoansOverviewActivity loansOverviewActivity = (AbstractLoansOverviewActivity) activity;
			loansOverviewActivity.deleteAllLoans(deleteFromHistory);
		}

	}

}
