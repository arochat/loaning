package com.aurelia.loaning.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoanFactory;
import com.aurelia.loaning.domain.LoanType;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
import com.aurelia.loaning.view.actionBar.delegate.AddLoanActionBarDelegate;
import com.aurelia.loaning.view.dialog.AddLoanDialogFragment;
import com.aurelia.loaning.view.validation.LoanFormValidator;

public class AddLoanActivity extends LoanFormActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setActionBarDelegate(new AddLoanActionBarDelegate());
		super.onCreate(savedInstanceState);

		loanType = (LoanType) getIntent().getExtras().get(AddLoanDialogFragment.LOAN_TYPE);

		switch (loanType) {
		case MONEY_LOAN:
			setContentView(R.layout.add_money_loan_form);
			break;
		case MONEY_BORROWING:
			setContentView(R.layout.add_money_borrowing_form);
			break;
		case OBJECT_LOAN:
			setContentView(R.layout.add_object_loan_form);
			break;
		case OBJECT_BORROWING:
			setContentView(R.layout.add_object_borrowing_form);
			break;
		default:
		}
	}

	// TODO : validate that form is completely filled
	public void saveLoan(View view) {

		LoanType loanType = getLoanFromUI().getLoanType();

		switch (loanType) {
		case MONEY_LOAN:
			// fallthrough
		case MONEY_BORROWING:
			if (LoanFormValidator.getInstance().moneyLoanIsValid(this)) {
				AbstractLoan loan = LoanFactory.createLoan(getLoanFromUI(), true);
				saveLoan(loan);
			}
			break;

		case OBJECT_LOAN:
			// fallthrough
		case OBJECT_BORROWING:
			if (LoanFormValidator.getInstance().objectLoanIsValid(this)) {
				AbstractLoan loan = LoanFactory.createLoan(getLoanFromUI(), true);
				saveLoan(loan);
			}
			break;

		default:
			// TODO : throw Exception
		}

	}

	private void saveLoan(AbstractLoan loan) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(Event.SAVE_LOANING.name(), loan);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(Event.SAVE_LOANING.name());
		intent.putExtras(bundle);
		startService(intent);
	}
}
