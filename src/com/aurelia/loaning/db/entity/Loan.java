package com.aurelia.loaning.db.entity;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.aurelia.loaning.db.annotation.Column;
import com.aurelia.loaning.db.annotation.Table;

@Table(name = "LOAN")
public class Loan implements Serializable {

	@Column(name = "loan_id", type = "integer", primaryKeyAutoIncrement = true)
	public Long id;

	@Column(name = "source", type = "text")
	public final String source; // owner

	@Column(name = "destination", type = "text")
	public final String destination; // beneficiary

	@Column(name = "start_date", type = "text")
	public final DateTime starteDate;

	@Column(name = "end_date", type = "text")
	public final DateTime endDate;

	@Column(name = "is_contact", type = "integer")
	public final boolean isContact;

	@Column(name = "type", type = "text", nullable = true)
	public String type;

	@Column(name = "description", type = "text")
	public final String description;

	public Loan(String source, String destination, DateTime starteDate, DateTime endDate, boolean isContact,
			String type, String description) {
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

	@Override
	public String toString() {
		return "Loan [source=" + source + ", destination=" + destination + ", starteDate=" + starteDate + ", endDate="
				+ endDate + ", isContact=" + isContact + ", type=" + type + ", description=" + description + "]";
	}

}
