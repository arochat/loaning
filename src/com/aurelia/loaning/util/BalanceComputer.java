package com.aurelia.loaning.util;

import java.util.List;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoanType;
import com.aurelia.loaning.domain.MoneyLoan;

public class BalanceComputer {

	public static Double computeBalance(List<AbstractLoan> loans) {
		Double balance = 0d;
		MoneyLoan moneyLoan;
		for (AbstractLoan loan : loans) {
			if (loan instanceof MoneyLoan) {
				moneyLoan = (MoneyLoan) loan;

				if (LoanType.MONEY_BORROWING.equals(moneyLoan.getType())) {
					balance -= moneyLoan.getAmount();
				} else if (LoanType.MONEY_LOAN.equals(moneyLoan.getType())) {
					balance += moneyLoan.getAmount();
				} else {
					// TODO : error
				}
			}
		}
		return balance;
	}

	public static String displayBalance(Double balance, String currency, String filter) {
		if (balance < 0) {
			return "I owe " + -balance + " " + currency + " to " + filter;
		}
		return filter + " owes me " + balance + " " + currency;
	}

}
