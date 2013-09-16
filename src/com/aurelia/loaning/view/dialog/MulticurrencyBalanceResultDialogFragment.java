package com.aurelia.loaning.view.dialog;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.util.BalanceComputer;
import com.aurelia.loaning.view.loansoverview.FilteredLoansOverviewActivity;

public class MulticurrencyBalanceResultDialogFragment extends DialogFragment {

	public static final String LOAN_TYPE = "type";

	private final DialogFragment callingDialog;
	private final Activity callingActivity;
	private final String selectedCurrency;

	String message = "Balance is computed using today's exchange rates between the different currencies";

	public MulticurrencyBalanceResultDialogFragment(Activity callingActivity, String selectedCurrency,
			DialogFragment callingDialog) {
		this.callingActivity = callingActivity;
		this.selectedCurrency = selectedCurrency;
		this.callingDialog = callingDialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		if (callingActivity instanceof FilteredLoansOverviewActivity) {
			FilteredLoansOverviewActivity filteredLoansOverviewActivity = (FilteredLoansOverviewActivity) callingActivity;
			List<AbstractLoan> loansToBalance = filteredLoansOverviewActivity.getLoans();
			String filter = filteredLoansOverviewActivity.getFilterString();
			if (loansToBalance != null && !loansToBalance.isEmpty()) {
				try {
					message = displayBalance(computeBalance(loansToBalance, selectedCurrency), selectedCurrency, filter)
							+ "\n\n" + message;
				} catch (ExecutionException e) {
					message = "You have loans in several currencies. The balance cannot be computed if you are not connected to Internet.";
				} catch (InterruptedException e) {
					message = "An error occured.";
				}
			}
		}

		return new AlertDialog.Builder(callingActivity)//
				.setMessage(message)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// close the dialog as well as the preceding one
						getDialog().cancel();
						callingDialog.dismiss();
					}
				}).create();

	}

	private Double computeBalance(List<AbstractLoan> loans, String currency) throws ExecutionException,
			InterruptedException {
		return BalanceComputer.computeBalanceInCurrency(loans, currency);
	}

	private String displayBalance(Double balance, String currency, String filter) {
		return BalanceComputer.displayBalance(balance, currency, filter);
	}
}
