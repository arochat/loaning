package com.aurelia.loaning.view.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.MoneyLoan;
import com.aurelia.loaning.util.BalanceComputer;
import com.aurelia.loaning.view.loansoverview.FilteredLoansOverviewActivity;

public class MulticurrencyBalanceResultDialogFragment extends DialogFragment {

	public static final String LOAN_TYPE = "type";

	private final Activity callingActivity;
	private final String selectedCurrency;

	String message = "Balance is computed using today's exchange rates between the different currencies";

	public MulticurrencyBalanceResultDialogFragment(Activity callingActivity, String selectedCurrency) {
		this.callingActivity = callingActivity;
		this.selectedCurrency = selectedCurrency;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		if (callingActivity instanceof FilteredLoansOverviewActivity) {
			FilteredLoansOverviewActivity filteredLoansOverviewActivity = (FilteredLoansOverviewActivity) callingActivity;
			List<AbstractLoan> loansToBalance = filteredLoansOverviewActivity.getLoans();
			String filter = filteredLoansOverviewActivity.getFilterString();
			if (loansToBalance != null && !loansToBalance.isEmpty()) {
				message = message
						+ "\n\n "
						+ displayBalance(computeBalance(convertLoansIntoCurrency(loansToBalance, selectedCurrency)),
								selectedCurrency, filter);
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

	private List<AbstractLoan> convertLoansIntoCurrency(List<AbstractLoan> loans, String currency) {
		List<AbstractLoan> convertedLoans = new ArrayList<AbstractLoan>();
		for (AbstractLoan loan : loans) {
			if (loan instanceof MoneyLoan) {
				try {
					MoneyLoan copiedLoan = (MoneyLoan) ((MoneyLoan) loan).clone();
					// TODO : fetch exchange rate here!!!
					copiedLoan.setAmount(20d);
					convertedLoans.add(copiedLoan);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return convertedLoans;
	}

	private Double computeBalance(List<AbstractLoan> loans) {
		return BalanceComputer.computeBalance(loans);
	}

	private String displayBalance(Double balance, String currency, String filter) {
		return BalanceComputer.displayBalance(balance, currency, filter);
	}
}
