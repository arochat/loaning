package com.aurelia.loaning.domain;

import org.joda.time.DateTime;

public class ObjectLoan extends AbstractLoan {

	public ObjectLoan(String person, DateTime startDate, DateTime notificationDate, boolean isContact, LoanType type) {
		super(person, startDate, notificationDate, isContact, type);
	}

	private String objectDefinition;

	public String getObjectDefinition() {
		return objectDefinition;
	}

	public void setObjectDefinition(String objectDefinition) {
		this.objectDefinition = objectDefinition;
	}
}
