package com.aurelia.loaning.domain;

import com.aurelia.loaning.db.Loan;

public class DomainToEntityConverter {

	public Loan convert(Transaction transaction) {

		Loan loan = new Loan(transaction.getSource(), transaction.getDestination(), transaction.getStarteDate(),
				transaction.getEndDate(), transaction.isContact(), transaction.getType(), transaction.getDescription());

		return loan;
	}

}
