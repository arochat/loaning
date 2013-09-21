package com.aurelia.loaning.view;

import android.widget.EditText;
import android.widget.TextView;

import com.aurelia.loaning.domain.LoanType;

public class AbstractLoanFromUI {

	private TextView notificationDateFromUI;
	private EditText personFromUI;
	private LoanType loanType;

	public EditText getPersonFromUI() {
		return personFromUI;
	}

	public void setPersonFromUI(EditText personFromUI) {
		this.personFromUI = personFromUI;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public TextView getNotificationDateFromUI() {
		return notificationDateFromUI;
	}

	public void setNotificationDateFromUI(TextView notificationDateFromUI) {
		this.notificationDateFromUI = notificationDateFromUI;
	}

}
