package com.aurelia.loaning.domain;

import org.joda.time.DateMidnight;

public class ObjectLoan extends AbstractLoan {

	private static final long serialVersionUID = -2604534372757109360L;

	public ObjectLoan(String person, DateMidnight startDate, DateMidnight notificationDate, boolean isContact,
			LoanType type, LoanStatus status) {
		super(person, startDate, notificationDate, isContact, type, status);
	}

	private String objectDefinition;

	@Override
	public String displayDescription() {
		return this.objectDefinition;
	}

	@Override
	public String displayNotification() {
		if (isBorrowing()) {
			return super.getPerson() + " loaned you " + objectDefinition;
		}
		return "You loaned " + objectDefinition + " to " + super.getPerson();
	}

	public String getObjectDefinition() {
		return objectDefinition;
	}

	public void setObjectDefinition(String objectDefinition) {
		this.objectDefinition = objectDefinition;
	}

}
