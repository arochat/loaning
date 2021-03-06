package com.aurelia.loaning.view.dialog;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.util.BalanceComputer;
import com.aurelia.loaning.view.loansoverview.FilteredLoansOverviewActivity;

public class BalanceResultDialogFragment extends DialogFragment {

	public static final String LOAN_TYPE = "type";

	private Activity callingActivity;

	public BalanceResultDialogFragment(Activity callingActivity) {
		this.callingActivity = callingActivity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		String message = "";

		if (callingActivity instanceof FilteredLoansOverviewActivity) {
			FilteredLoansOverviewActivity filteredLoansOverviewActivity = (FilteredLoansOverviewActivity) callingActivity;
			List<AbstractLoan> loansToBalance = filteredLoansOverviewActivity.getLoans();
			String filter = filteredLoansOverviewActivity.getFilterString();
			if (loansToBalance != null && !loansToBalance.isEmpty()) {
				message = displayBalance(computeBalance(loansToBalance), "CHF", filter);
			}
		}

		return new AlertDialog.Builder(callingActivity)//
				.setMessage(message)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// juste close the popup
					}
				}).create();

	}

	private Double computeBalance(List<AbstractLoan> loans) {
		return BalanceComputer.computeBalance(loans);
	}

	private String displayBalance(Double balance, String currency, String filter) {
		return BalanceComputer.displayBalance(balance, currency, filter);
	}
}
