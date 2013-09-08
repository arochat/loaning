package com.aurelia.loaning.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoanType;
import com.aurelia.loaning.domain.MoneyLoan;
import com.aurelia.loaning.domain.ObjectLoan;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.util.Currency;
import com.aurelia.loaning.view.loansoverview.StandardLoansOverviewActivity;

public abstract class LoanFormActivity extends BaseActivity {

	public final static String ME = "ME";

	private BroadcastReceiver dbFeedbackReceiver;
	private IntentFilter intentFilter;
	protected LoanType loanType;
	protected List<String> currenciesList;

	@Override
	protected void onResume() {
		super.onResume();
		if (dbFeedbackReceiver == null) {
			dbFeedbackReceiver = new DbFeedbackReceiver();
			intentFilter = new IntentFilter(Event.LOAN_MODIFIED.name());
		}
		registerReceiver(dbFeedbackReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(dbFeedbackReceiver);
	}

	protected Spinner prepareCurrenciesSpinner() {
		Spinner currenciesSpinner = (Spinner) findViewById(R.id.spinnerCurrencies);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				getCurrenciesList());

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		currenciesSpinner.setAdapter(dataAdapter);
		currenciesSpinner.setPrompt(getString(R.string.currencies_prompt));
		return currenciesSpinner;
	}

	protected long prefillForm() {
		long loanId;

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

			// dropdown list for currencies
			Spinner currenciesSpinner = prepareCurrenciesSpinner();
			currenciesSpinner.setSelection(getCurrenciesList().indexOf(moneyLoan.getCurrency()));

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
		return loanId;

	}

	protected List<String> getCurrenciesList() {
		if (currenciesList == null) {
			Collection<String> currencies = Currency.getPrintableValues();
			currenciesList = new ArrayList<String>(currencies);
		}
		return currenciesList;
	}

	private void setCommonData(AbstractLoan loan) {
		// common to all types of loans
		EditText toTextField = (EditText) this.findViewById(R.id.person_field);
		toTextField.setText(loan.getPerson(), TextView.BufferType.EDITABLE);
	}

	// --------------------------------------------

	public LoanType getLoanType() {
		return loanType;
	}

	public abstract void saveLoan(View view);

	public AbstractLoanFromUI getLoanFromUI() {
		return new LoanFromUIBuilder(this).getLoanFromUI();
	}

	private class DbFeedbackReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Event.LOAN_MODIFIED.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, StandardLoansOverviewActivity.class);
				startActivity(displayLoansIntent);
			}
		}
	}
}
