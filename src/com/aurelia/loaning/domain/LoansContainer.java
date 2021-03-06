package com.aurelia.loaning.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoansContainer implements Serializable {

	private static final long serialVersionUID = 3630509805394142161L;

	private List<AbstractLoan> loans;

	public List<AbstractLoan> getLoans() {
		if (loans != null && !loans.isEmpty()) {
			return Collections.unmodifiableList(this.loans);
		}
		return new ArrayList<AbstractLoan>();
	}

	public void setLoans(List<AbstractLoan> loans) {
		this.loans = loans;
	}

}
