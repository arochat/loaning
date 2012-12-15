package com.aurelia.loaning.view;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

public class LoanFromUI {

	private EditText descriptionFromUI;
	private RadioButton loanRadio;
	private RadioButton borrowingRadio;
	private EditText destinationFromUI;
	private DatePicker endDateFromUI;

	public EditText getDescriptionFromUI() {
		return descriptionFromUI;
	}

	public RadioButton getLoanRadio() {
		return loanRadio;
	}

	public RadioButton getBorrowingRadio() {
		return borrowingRadio;
	}

	public EditText getDestinationFromUI() {
		return destinationFromUI;
	}

	public DatePicker getEndDateFromUI() {
		return endDateFromUI;
	}

	public void setDescriptionFromUI(EditText descriptionFromUI) {
		this.descriptionFromUI = descriptionFromUI;
	}

	public void setLoanRadio(RadioButton loanRadio) {
		this.loanRadio = loanRadio;
	}

	public void setBorrowingRadio(RadioButton borrowingRadio) {
		this.borrowingRadio = borrowingRadio;
	}

	public void setDestinationFromUI(EditText destinationFromUI) {
		this.destinationFromUI = destinationFromUI;
	}

	public void setEndDateFromUI(DatePicker endDateFromUI) {
		this.endDateFromUI = endDateFromUI;
	}

}
