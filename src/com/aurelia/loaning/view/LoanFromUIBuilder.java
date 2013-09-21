package com.aurelia.loaning.view;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aurelia.loaning.R;

public class LoanFromUIBuilder {

	private AbstractLoanFromUI loanFromUI;

	public LoanFromUIBuilder(LoanFormActivity activity) {

		switch (activity.getLoanType()) {
		case MONEY_LOAN:
			// fallthrough
		case MONEY_BORROWING:
			MoneyLoanFromUI moneyLoanFromUI = new MoneyLoanFromUI();
			moneyLoanFromUI.setAmountFromUI((EditText) activity.findViewById(R.id.amount_field));
			moneyLoanFromUI.setCurrencyFromUI((Spinner) activity.findViewById(R.id.spinnerCurrencies));
			moneyLoanFromUI.setReasonFromUI((EditText) activity.findViewById(R.id.reason_field));
			moneyLoanFromUI.setNotificationDateFromUI((TextView) activity.findViewById(R.id.reminder_label));
			moneyLoanFromUI.setPersonFromUI((EditText) activity.findViewById(R.id.person_field));
			moneyLoanFromUI.setLoanType(activity.getLoanType());
			loanFromUI = moneyLoanFromUI;
			break;
		case OBJECT_LOAN:
			// fallthrough
		case OBJECT_BORROWING:
			ObjectLoanFromUI objectLoanFromUI = new ObjectLoanFromUI();
			objectLoanFromUI.setObjectDefinitionFromUI((EditText) activity.findViewById(R.id.description_field));
			// objectLoanFromUI.setEndDateFromUI(endDateFromUI);
			objectLoanFromUI.setPersonFromUI((EditText) activity.findViewById(R.id.person_field));
			objectLoanFromUI.setLoanType(activity.getLoanType());
			objectLoanFromUI.setNotificationDateFromUI((TextView) activity.findViewById(R.id.reminder_label));
			loanFromUI = objectLoanFromUI;
			break;
		default:
			// TODO : exception
		}
	}

	public AbstractLoanFromUI getLoanFromUI() {
		return loanFromUI;
	}

}
