package com.aurelia.loaning.domain;

import org.joda.time.DateTime;

public class MoneyLoan extends AbstractLoan implements Cloneable {

	private static final long serialVersionUID = 187223114061410368L;

	private double amount;
	private String currency;
	private String reason;

	@Override
	public String displayDescription() {
		return new StringBuilder().append(this.amount).append(" ").append(this.currency).append(" for ")
				.append(this.reason).toString();
	}

	public MoneyLoan(String person, DateTime startDate, DateTime notificationDate, boolean isContact, LoanType type,
			LoanStatus status) {
		super(person, startDate, notificationDate, isContact, type, status);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		MoneyLoan cloned = (MoneyLoan) super.clone();
		return cloned;
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
