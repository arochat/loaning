package com.aurelia.loaning.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoanFactory;
import com.aurelia.loaning.domain.MoneyLoan;
import com.aurelia.loaning.domain.ObjectLoan;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
import com.aurelia.loaning.view.actionBar.delegate.AddLoanActionBarDelegate;

public class EditLoanActivity extends LoanFormActivity {

	private Long loanId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setActionBarDelegate(new AddLoanActionBarDelegate());
		super.onCreate(savedInstanceState);

		AbstractLoan loan = (AbstractLoan) getIntent().getExtras().get("loan");
		loanType = loan.getType();
		loanId = loan.getId();

		switch (loanType) {
		case MONEY_LOAN:
			// fallthrough
		case MONEY_BORROWING:
			setContentView(R.layout.add_money_loan_form);
			MoneyLoan moneyLoan = (MoneyLoan) loan;

			EditText amountTextField = (EditText) this.findViewById(R.id.amount_field);
			amountTextField.setText(String.valueOf(moneyLoan.getAmount()), TextView.BufferType.EDITABLE);

			EditText currencyTextField = (EditText) this.findViewById(R.id.currency_field);
			currencyTextField.setText(moneyLoan.getCurrency(), TextView.BufferType.EDITABLE);

			EditText reasonTextField = (EditText) this.findViewById(R.id.reason_field);
			reasonTextField.setText(moneyLoan.getReason(), TextView.BufferType.EDITABLE);

			setCommonData(moneyLoan);
			break;
		case OBJECT_LOAN:
			// fallthrough
		case OBJECT_BORROWING:
			setContentView(R.layout.add_object_loan_form);

			ObjectLoan objectLoan = (ObjectLoan) loan;

			EditText descriptionTextField = (EditText) this.findViewById(R.id.description_field);
			descriptionTextField.setText(objectLoan.getObjectDefinition(), TextView.BufferType.EDITABLE);

			setCommonData(objectLoan);

			break;
		default:
		}
	}

	public void saveLoan(View view) {

		AbstractLoan loan = LoanFactory.createLoan(getLoanFromUI(), false);
		loan.setId(loanId);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Event.UPDATE_LOAN.name(), loan);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(Event.UPDATE_LOAN.name());
		intent.putExtras(bundle);
		startService(intent);
	}

	private void setCommonData(AbstractLoan loan) {
		// common to all types of loans
		EditText toTextField = (EditText) this.findViewById(R.id.person_field);
		toTextField.setText(loan.getPerson(), TextView.BufferType.EDITABLE);
	}

}
