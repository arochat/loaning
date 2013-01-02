package com.aurelia.loaning.domain;

import java.io.Serializable;

import org.joda.time.DateTime;

public abstract class Transaction implements Serializable {

	// TODO : differenciate toString methods

	private static final long serialVersionUID = -7109576915787405556L;

	public final static String ME = "ME";

	private long id;
	private String source; // owner
	private String destination; // beneficiary
	private DateTime startDate;
	private DateTime endDate;
	private boolean isContact;
	private String type;
	private String description;

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public boolean isContact() {
		return isContact;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public void setContact(boolean isContact) {
		this.isContact = isContact;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Transaction [source=" + source + ", destination=" + destination + ", starteDate=" + startDate
				+ ", endDate=" + endDate + ", isContact=" + isContact + ", type=" + type + ", description="
				+ description + "]";
	}
}
