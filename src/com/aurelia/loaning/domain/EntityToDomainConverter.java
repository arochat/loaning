package com.aurelia.loaning.domain;

import com.aurelia.loaning.db.entity.Loan;

public class EntityToDomainConverter {

	public Transaction convert(Loan loan) {

		Transaction transaction;

		if (Transaction.ME.equals(loan.destination)) {
			transaction = new BorrowingTransaction();
		} else {
			transaction = new LoanTransaction();
		}
		transaction.setDestination(loan.destination);
		transaction.setSource(loan.source);
		transaction.setDescription(loan.description);
		transaction.setType(loan.type);
		transaction.setStarteDate(loan.starteDate);
		transaction.setEndDate(loan.endDate);
		transaction.setContact(loan.isContact);

		return transaction;
	}

}
