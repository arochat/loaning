package com.aurelia.loaning.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

import com.aurelia.loaning.domain.AbstractLoan.LoanStatus;
import com.aurelia.loaning.view.AbstractLoanFromUI;
import com.aurelia.loaning.view.MoneyLoanFromUI;
import com.aurelia.loaning.view.ObjectLoanFromUI;

public class LoanFactory {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

	public static AbstractLoan createLoan(AbstractLoanFromUI loanFromUI, boolean newLoan) {

		LoanType loanType = loanFromUI.getLoanType();

		MoneyLoan moneyLoan = null;
		ObjectLoan objectLoan = null;
		DateMidnight startDate = null;
		DateMidnight notificationDate = null;

		if (newLoan) {
			startDate = DateMidnight.now();
		}
		try {
			notificationDate = setNotificationDate(loanFromUI);
		} catch (ParseException e) {
			// TODO : what to do here ?
			notificationDate = null;
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
			moneyLoan.setNotificationDate(notificationDate);

			return moneyLoan;

		case OBJECT_LOAN:
			// fallthrough
		case OBJECT_BORROWING:
			ObjectLoanFromUI objectLoanFromUI = (ObjectLoanFromUI) loanFromUI;
			objectLoan = new ObjectLoan(person, startDate, notificationDate, isContact, loanType, LoanStatus.ACTIVE);
			objectLoan.setObjectDefinition(objectLoanFromUI.getObjectDefinitionFromUI().getText().toString());
			objectLoan.setNotificationDate(notificationDate);
			return objectLoan;

		default:
			// TODO : throw Exception
		}
		return null;

	}

	private static DateMidnight setNotificationDate(AbstractLoanFromUI loanFromUI) throws ParseException {

		DateTime notificationDate = new DateTime(dateFormat.parse(loanFromUI.getNotificationDateFromUI().getText()
				.toString()));

		return notificationDate.toDateMidnight();
	}
}
