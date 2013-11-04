package com.aurelia.loaning.db.entity;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.aurelia.loaning.db.annotation.Column;
import com.aurelia.loaning.db.annotation.Table;
import com.aurelia.loaning.domain.AbstractLoan.LoanStatus;

@Table(name = "LOAN")
public class Loan implements Serializable {

	// TODO : try to get rid of the following constraints

	// public because of ClasspathScanner needs to access the fields in
	// findAnnotatedFields
	// not final because DatabaseAccess needs to invoke an empty constructor
	// setters because DatabaseAccess needs to invoke the setters

	@Column(name = "loan_id", type = "integer", primaryKeyAutoIncrement = true)
	public Long id;

	@Column(name = "source", type = "text")
	public String source; // owner

	@Column(name = "destination", type = "text")
	public String destination; // beneficiary

	@Column(name = "start_date", type = "text")
	public DateTime startDate;

	@Column(name = "end_date", type = "text")
	public DateTime endDate;

	@Column(name = "is_contact", type = "integer")
	public boolean isContact;

	@Column(name = "type", type = "text", nullable = true)
	public String type;

	@Column(name = "description", type = "text")
	public String description;

	@Column(name = "STATUS", type = "integer")
	public Integer status;

	public Loan() {
		super();
	}

	public Loan(String source, String destination, DateTime starteDate, DateTime endDate, boolean isContact,
			String type, String description, Integer status) {
		super();
		this.source = source;
		this.destination = destination;
		this.startDate = starteDate;
		this.endDate = endDate;
		this.isContact = isContact;
		this.type = type;
		this.description = description;
		this.status = status;
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

	public void setIsContact(boolean isContact) {
		this.isContact = isContact;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

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

	public boolean getIsContact() {
		return isContact;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Loan [source=" + source + ", destination=" + destination + ", starteDate=" + startDate + ", endDate="
				+ endDate + ", status=" + LoanStatus.getLoanStatus(status).name() + ", isContact=" + isContact
				+ ", type=" + type + ", description=" + description + "]";
	}

}
