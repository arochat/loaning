package com.aurelia.loaning.domain;

import org.joda.time.DateTime;

import com.aurelia.loaning.domain.AbstractLoan.LoanStatus;
import com.aurelia.loaning.view.AbstractLoanFromUI;
import com.aurelia.loaning.view.MoneyLoanFromUI;
import com.aurelia.loaning.view.ObjectLoanFromUI;

public class LoanFactory {

	public static AbstractLoan createLoan(AbstractLoanFromUI loanFromUI, boolean newLoan) {

		LoanType loanType = loanFromUI.getLoanType();

		MoneyLoan moneyLoan = null;
		ObjectLoan objectLoan = null;
		DateTime startDate = null;
		DateTime notificationDate = null;

		// TODO : bad design, risk of NPE!!
		if (newLoan) {
			startDate = DateTime.now();
			// TODO : when implementing notification, notificationDate could be
			// modified in the UI
			notificationDate = DateTime.now();
		}

		boolean isContact = false;
		String person = loanFromUI.getPersonFromUI().getText().toString();

		switch (loanType) {
		case MONEY_LOAN:
			// fallthrough
		case MONEY_BORROWING:
			MoneyLoanFromUI moneyLoanFromUI = (MoneyLoanFromUI) loanFromUI;
			moneyLoan = new MoneyLoan(person, startDate, notificationDate, isContact, loanType, LoanStatus.ACTIVE);
			moneyLoan.setAmount(Double.valueOf(moneyLoanFromUI.getAmountFromUI().getText().toString()));
			moneyLoan.setCurrency(moneyLoanFromUI.getCurrencyFromUI().getSelectedItem().toString());
			moneyLoan.setReason(moneyLoanFromUI.getReasonFromUI().getText().toString());
			return moneyLoan;

		case OBJECT_LOAN:
			// fallthrough
		case OBJECT_BORROWING:
			ObjectLoanFromUI objectLoanFromUI = (ObjectLoanFromUI) loanFromUI;
			objectLoan = new ObjectLoan(person, startDate, notificationDate, isContact, loanType, LoanStatus.ACTIVE);
			objectLoan.setObjectDefinition(objectLoanFromUI.getObjectDefinitionFromUI().getText().toString());
			return objectLoan;

		default:
			// TODO : throw Exception
		}
		return null;

		// needs to add 1 to the month got from the UI. DatePicker months range
		// from 0 to 11 whereas JodaTime months range from 1 to 12.
		// DateTime endDate = new
		// DateTime(loanFromUI.getEndDateFromUI().getYear(),
		// loanFromUI.getEndDateFromUI()
		// .getMonth() + 1, loanFromUI.getEndDateFromUI().getDayOfMonth(), 0, 0,
		// 0);
		// transaction.setEndDate(endDate);
	}
}
