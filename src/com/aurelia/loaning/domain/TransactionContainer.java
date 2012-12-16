package com.aurelia.loaning.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class TransactionContainer implements Serializable {

	private static final long serialVersionUID = 3630509805394142161L;

	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(this.transactions);
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
