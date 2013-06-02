package com.aurelia.loaning.view.actionBar.action;

import android.content.Intent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.util.LoanUtil;
import com.aurelia.loaning.view.DisplayDetailActivity;
import com.aurelia.loaning.view.EditLoanActivity;

public class EditLoanCommand implements ActionBarCommand {

	@Override
	public void performAction(SherlockFragmentActivity callingActivity) {

		if (callingActivity instanceof DisplayDetailActivity) {
			DisplayDetailActivity detailActivity = (DisplayDetailActivity) callingActivity;
			Intent intent = LoanUtil.createIntentWithBundledLoan(detailActivity, EditLoanActivity.class,
					detailActivity.getDisplayedLoan());
			callingActivity.startActivity(intent);
		}
	}
}
