package com.aurelia.loaning.view.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.MoneyLoan;
import com.aurelia.loaning.view.loansoverview.FilteredLoansOverviewActivity;

public class ChooseCurrencyDialogFragment extends DialogFragment {

	public static final String LOAN_TYPE = "type";

	private Activity callingActivity;

	private String[] loansCurrencies;

	public ChooseCurrencyDialogFragment(Activity callingActivity) {
		this.callingActivity = callingActivity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		if (callingActivity instanceof FilteredLoansOverviewActivity) {
			FilteredLoansOverviewActivity filteredLoansOverviewActivity = (FilteredLoansOverviewActivity) callingActivity;

			return new AlertDialog.Builder(callingActivity)
			//
					.setTitle("Choose the balance currency")
					//
					.setSingleChoiceItems(loansCurrencies(filteredLoansOverviewActivity.getLoans()), 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialogInterface, int item) {
									// no special event when selecting an item

								}
							})
					//
					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
							displayBalanceComputationFragment((SherlockFragmentActivity) callingActivity,
									loansCurrencies[selectedPosition]);
						}
					}).create();

		} else {
			return null;
		}

	}

	private void displayBalanceComputationFragment(SherlockFragmentActivity callingActivity, String selectedCurrency) {

		FragmentTransaction ft = callingActivity.getSupportFragmentManager().beginTransaction();
		Fragment prev = callingActivity.getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		MulticurrencyBalanceResultDialogFragment balanceResultDialogFragment = new MulticurrencyBalanceResultDialogFragment(
				callingActivity, selectedCurrency, this);
		balanceResultDialogFragment.show(ft, "dialog");
	}

	private String[] loansCurrencies(List<AbstractLoan> loans) {
		List<String> currencies = new ArrayList<String>();

		for (AbstractLoan loan : loans) {
			if (loan instanceof MoneyLoan) {
				MoneyLoan moneyLoan = (MoneyLoan) loan;
				if (!currencies.contains(moneyLoan.getCurrency())) {
					currencies.add(moneyLoan.getCurrency());
				}
			}
		}

		String[] strArray = new String[currencies.size()];
		for (int i = 0; i < currencies.toArray().length; i++) {
			strArray[i] = new String((String) currencies.toArray()[i]);
		}
		loansCurrencies = strArray;
		return strArray;
	}

}
