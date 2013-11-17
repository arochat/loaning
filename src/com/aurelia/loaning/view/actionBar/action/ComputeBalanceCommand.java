package com.aurelia.loaning.view.actionBar.action;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.MoneyLoan;
import com.aurelia.loaning.view.dialog.BalanceResultDialogFragment;
import com.aurelia.loaning.view.dialog.ChooseCurrencyDialogFragment;
import com.aurelia.loaning.view.loansoverview.FilteredLoansOverviewActivity;

public class ComputeBalanceCommand implements ActionBarCommand {

	@Override
	public void performAction(SherlockFragmentActivity callingActivity) {

		if (callingActivity instanceof FilteredLoansOverviewActivity) {

			FilteredLoansOverviewActivity activity = (FilteredLoansOverviewActivity) callingActivity;

			FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
			Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);
			if (!needsMulticurrencyConversion(activity.getLoans())) {
				BalanceResultDialogFragment balanceResultDialogFragment = new BalanceResultDialogFragment(activity);
				balanceResultDialogFragment.show(ft, "dialog");
			} else {
				ChooseCurrencyDialogFragment balanceResultDialogFragment = new ChooseCurrencyDialogFragment(activity);
				balanceResultDialogFragment.show(ft, "dialog");
			}
		}

	}

	private boolean needsMulticurrencyConversion(List<AbstractLoan> loans) {
		List<String> currencies = new ArrayList<String>();

		for (AbstractLoan loan : loans) {
			if (loan instanceof MoneyLoan) {
				MoneyLoan moneyLoan = (MoneyLoan) loan;
				if (!currencies.contains(moneyLoan.getCurrency())) {
					currencies.add(moneyLoan.getCurrency());
				}
			}
		}
		return currencies.size() > 1;
	}
}
