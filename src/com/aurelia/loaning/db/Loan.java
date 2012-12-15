package com.aurelia.loaning.db;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Loan implements Serializable {

	private final String source; // owner
	private final String destination; // beneficiary
	private final DateTime starteDate;
	private final DateTime endDate;
	private final boolean isContact;
	private String type;
	private final String description;

	public Loan(String source, String destination, DateTime starteDate,
			DateTime endDate, boolean isContact, String type, String description) {
		super();
		this.source = source;
		this.destination = destination;
		this.starteDate = starteDate;
		this.endDate = endDate;
		this.isContact = isContact;
		this.type = type;
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public DateTime getStarteDate() {
		return starteDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public Boolean getIsContact() {
		return isContact;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Loan [source=" + source + ", destination=" + destination
				+ ", starteDate=" + starteDate + ", endDate=" + endDate
				+ ", isContact=" + isContact + ", type=" + type
				+ ", description=" + description + "]";
	}

}
