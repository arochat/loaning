package com.aurelia.loaning.view.actionBar.action;

import android.app.Activity;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.dialog.ConfirmationDialogFragment;
import com.aurelia.loaning.view.loansoverview.FilteredLoansOverviewActivity;

public class SettleAllCommand extends ActionBarCommandWithConfirmation {

	@Override
	public void performAction(SherlockFragmentActivity activity) {
		super.setConfirmationMessage(activity.getResources().getString(R.string.confirmation_settle_all));
		super.performActionWithDialogFragment(activity, new ConfirmationDialogFragment(activity, this));
	}

	@Override
	public void performActionCallback(Activity activity) {
		if (activity instanceof FilteredLoansOverviewActivity) {
			FilteredLoansOverviewActivity filteredLoansOverviewActivity = (FilteredLoansOverviewActivity) activity;
			filteredLoansOverviewActivity.settleAllLoans();
		}
	}

}
