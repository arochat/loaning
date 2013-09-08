package com.aurelia.loaning.view;

import android.widget.EditText;
import android.widget.Spinner;

public class MoneyLoanFromUI extends AbstractLoanFromUI {

	private EditText reasonFromUI;
	private EditText amountFromUI;
	private Spinner currencyFromUI;

	public EditText getReasonFromUI() {
		return reasonFromUI;
	}

	public void setReasonFromUI(EditText reasonFromUI) {
		this.reasonFromUI = reasonFromUI;
	}

	public EditText getAmountFromUI() {
		return amountFromUI;
	}

	public void setAmountFromUI(EditText amountFromUI) {
		this.amountFromUI = amountFromUI;
	}

	public void setCurrencyFromUI(Spinner currencyFromUI) {
		this.currencyFromUI = currencyFromUI;
	}

	public Spinner getCurrencyFromUI() {
		return currencyFromUI;
	}

}
