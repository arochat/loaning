package com.aurelia.loaning.view;

import android.widget.DatePicker;
import android.widget.EditText;

import com.aurelia.loaning.domain.LoanType;

public class AbstractLoanFromUI {

	private EditText personFromUI;
	private DatePicker endDateFromUI;
	private LoanType loanType;

	public EditText getPersonFromUI() {
		return personFromUI;
	}

	public void setPersonFromUI(EditText personFromUI) {
		this.personFromUI = personFromUI;
	}

	public DatePicker getEndDateFromUI() {
		return endDateFromUI;
	}

	public void setEndDateFromUI(DatePicker endDateFromUI) {
		this.endDateFromUI = endDateFromUI;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

}
