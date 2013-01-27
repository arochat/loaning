package com.aurelia.loaning.domain;

import org.joda.time.DateTime;

public class MoneyLoan extends AbstractLoan {

	private double amount;
	private String currency;
	private String reason;

	@Override
	public String displayDescription() {
		return new StringBuilder().append(this.amount).append(" ").append(this.currency).append(" for ")
				.append(this.reason).toString();
	}

	public MoneyLoan(String person, DateTime startDate, DateTime notificationDate, boolean isContact, LoanType type) {
		super(person, startDate, notificationDate, isContact, type);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
