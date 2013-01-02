package com.aurelia.loaning.domain;

import com.aurelia.loaning.db.entity.Loan;

public class EntityToDomainConverter {

	public Transaction convert(Loan loan) {

		Transaction transaction;

		if (Transaction.ME.equals(loan.getDestination())) {
			transaction = new BorrowingTransaction();
		} else {
			transaction = new LoanTransaction();
		}
		transaction.setId(loan.getId());
		transaction.setDestination(loan.getDestination());
		transaction.setSource(loan.getSource());
		transaction.setDescription(loan.getDescription());
		transaction.setType(loan.getType());
		transaction.setStartDate(loan.getStartDate());
		transaction.setEndDate(loan.getEndDate());
		transaction.setContact(loan.getIsContact());

		return transaction;
	}

}
