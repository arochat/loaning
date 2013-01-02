package com.aurelia.loaning.domain;

import com.aurelia.loaning.db.entity.Loan;

public class DomainToEntityConverter {

	public Loan convert(Transaction transaction) {

		Loan loan = new Loan(transaction.getSource(), transaction.getDestination(), transaction.getStartDate(),
				transaction.getEndDate(), transaction.isContact(), transaction.getType(), transaction.getDescription());

		loan.setId(transaction.getId());

		return loan;
	}

}
