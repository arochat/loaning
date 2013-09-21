package com.aurelia.loaning.domain;

import java.io.Serializable;

import org.joda.time.DateMidnight;

public abstract class AbstractLoan implements Serializable {

	private static final long serialVersionUID = -2002043834197778429L;

	private long id;
	private String person;
	private DateMidnight startDate;
	private DateMidnight notificationDate;
	private boolean isContact;
	private LoanType type;
	private LoanStatus status;

	public AbstractLoan(String person, DateMidnight startDate, DateMidnight notificationDate, boolean isContact,
			LoanType type, LoanStatus status) {
		super();
		this.person = person;
		this.startDate = startDate;
		this.notificationDate = notificationDate;
		this.isContact = isContact;
		this.type = type;
		this.status = status;
	}

	public String displayPerson() {
		if (isBorrowing()) {
			return new StringBuilder().append("I borrowed from ").append(this.getPerson()).toString();
		} else {
			return new StringBuilder().append("I loaned to ").append(this.getPerson()).toString();
		}
	}

	public abstract String displayDescription();

	public boolean isMoneyLoan() {
		return LoanType.MONEY_LOAN.equals(this.getType()) || LoanType.MONEY_BORROWING.equals(this.getType());
	}

	public boolean isBorrowing() {
		return LoanType.OBJECT_BORROWING.equals(this.getType()) || LoanType.MONEY_BORROWING.equals(this.getType());
	}

	public enum LoanStatus {

		ACTIVE(1), SETTLED(2), UNKNOWN(99);

		private int status;

		private LoanStatus(int status) {
			this.status = status;
		}

		public int getValue() {
			return this.status;
		}

		public static LoanStatus getLoanStatus(int status) {
			for (LoanStatus loanStatus : LoanStatus.values()) {
				if (loanStatus.getValue() == status) {
					return loanStatus;
				}
			}
			return UNKNOWN;
		}
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

	public DateMidnight getStartDate() {
		return startDate;
	}

	public void setStartDate(DateMidnight startDate) {
		this.startDate = startDate;
	}

	public DateMidnight getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(DateMidnight notificationDate) {
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

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

}
