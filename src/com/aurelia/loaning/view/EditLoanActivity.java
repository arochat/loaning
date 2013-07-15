package com.aurelia.loaning.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoanFactory;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
import com.aurelia.loaning.view.actionBar.delegate.AddLoanActionBarDelegate;

public class EditLoanActivity extends LoanFormActivity {

	private Long loanId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setActionBarDelegate(new AddLoanActionBarDelegate());
		super.onCreate(savedInstanceState);

		loanId = prefillForm();
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

}
