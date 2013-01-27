package com.aurelia.loaning.domain;

import java.io.Serializable;

import org.joda.time.DateTime;

public abstract class AbstractLoan implements Serializable {

	private static final long serialVersionUID = -2002043834197778429L;

	private long id;
	private String person;
	private DateTime startDate;
	private DateTime notificationDate;
	private boolean isContact;
	private LoanType type;

	public AbstractLoan(String person, DateTime startDate, DateTime notificationDate, boolean isContact, LoanType type) {
		super();
		this.person = person;
		this.startDate = startDate;
		this.notificationDate = notificationDate;
		this.isContact = isContact;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(DateTime notificationDate) {
		this.notificationDate = notificationDate;
	}

	public boolean isContact() {
		return isContact;
	}

	public void setContact(boolean isContact) {
		this.isContact = isContact;
	}

	public LoanType getType() {
		return type;
	}

	public void setType(LoanType type) {
		this.type = type;
	}

	// needs to add 1 to the month got from the UI. DatePicker months range
	// from 0 to 11 whereas JodaTime months range from 1 to 12.
	// DateTime endDate = new
	// DateTime(loanFromUI.getEndDateFromUI().getYear(),
	// loanFromUI.getEndDateFromUI()
	// .getMonth() + 1, loanFromUI.getEndDateFromUI().getDayOfMonth(), 0, 0,
	// 0);
	// transaction.setEndDate(endDate);
}
