package com.aurelia.loaning.domain;

import org.joda.time.DateTime;

import com.aurelia.loaning.view.LoanFromUI;

public class TransactionFactory {

	public static Transaction createTransaction(LoanFromUI loanFromUI) {
		Transaction transaction;

		if (loanFromUI.getLoanRadio().isChecked()) {
			transaction = new LoanTransaction();
			transaction.setSource(Transaction.ME);
			transaction.setDestination(loanFromUI.getDestinationFromUI().getText().toString());
		} else {
			transaction = new BorrowingTransaction();
			transaction.setSource(loanFromUI.getDestinationFromUI().getText().toString());
			transaction.setDestination(Transaction.ME);
		}

		transaction.setDescription(loanFromUI.getDescriptionFromUI().getText().toString());
		DateTime endDate = new DateTime(loanFromUI.getEndDateFromUI().getYear(), loanFromUI.getEndDateFromUI()
				.getMonth(), loanFromUI.getEndDateFromUI().getDayOfMonth(), 0, 0, 0);
		transaction.setStarteDate(DateTime.now());
		transaction.setEndDate(endDate);
		transaction.setContact(false);
		transaction.setType("noType");

		return transaction;
	}

}
