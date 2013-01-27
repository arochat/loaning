package com.aurelia.loaning.view;

import android.widget.EditText;

public class MoneyLoanFromUI extends AbstractLoanFromUI {

	private EditText reasonFromUI;
	private EditText amountFromUI;
	private EditText currencyFromUI;

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

	public EditText getCurrencyFromUI() {
		return currencyFromUI;
	}

	public void setCurrencyFromUI(EditText currencyFromUI) {
		this.currencyFromUI = currencyFromUI;
	}

}
