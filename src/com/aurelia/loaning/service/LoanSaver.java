package com.aurelia.loaning.service;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.aurelia.loaning.db.LoanDatabaseAccess;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.AbstractLoan.LoanStatus;
import com.aurelia.loaning.domain.DomainToEntityConverter;
import com.aurelia.loaning.event.Event;

public class LoanSaver extends IntentService {

	private LoanDatabaseAccess databaseAccess;
	private DomainToEntityConverter converter;

	public LoanSaver() {
		super(LoanSaver.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(final Intent intent) {

		// Loan or List<Loan> received in intent
		Bundle bundle = intent.getExtras();

		if (databaseAccess == null) {
			databaseAccess = new LoanDatabaseAccess(this);
			databaseAccess.open();
		}
		if (converter == null) {
			converter = new DomainToEntityConverter();
		}

		long dbResult = -1l;

		if (Event.SAVE_LOANING.name().equals(intent.getAction())) {
			dbResult = saveLoan(bundle);
		} else if (Event.DELETE_LOAN.name().equals(intent.getAction())) {
			dbResult = deleteLoan(bundle);
		} else if (Event.SETTLE_LOAN.name().equals(intent.getAction())) {
			dbResult = settleLoan(bundle);
		} else if (Event.UPDATE_LOAN.name().equals(intent.getAction())) {
			dbResult = updateLoan(bundle);
		} else if (Event.SETTLE_ALL_FILTERED_LOANS.name().equals(intent.getAction())) {
			dbResult = settleAllLoans(bundle);
		}

		if (dbResult != -1) {
			sendBroadcast(new Intent(Event.LOAN_MODIFIED.name()));
		}
		databaseAccess.close();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
		// TODO : what do we need to return here?
	}

	private long updateLoan(Bundle loanToUpdate) {
		AbstractLoan loan = (AbstractLoan) loanToUpdate.getSerializable(Event.UPDATE_LOAN.name());
		return databaseAccess.updateLoan(converter.convert(loan));
	}

	private long saveLoan(Bundle loanToSave) {
		AbstractLoan loan = (AbstractLoan) loanToSave.getSerializable(Event.SAVE_LOANING.name());
		return databaseAccess.insert(converter.convert(loan));
	}

	private long deleteLoan(Bundle loanToDelete) {
		AbstractLoan loan = (AbstractLoan) loanToDelete.getSerializable(Event.DELETE_LOAN.name());
		return databaseAccess.delete(converter.convert(loan));
	}

	private int settleLoan(Bundle loanToSettle) {
		AbstractLoan loan = (AbstractLoan) loanToSettle.getSerializable(Event.SETTLE_LOAN.name());
		return databaseAccess.updateStatus(converter.convert(loan), LoanStatus.SETTLED.getValue());
	}

	private long settleAllLoans(Bundle loansToSettle) {
		List<Long> loanIdsToSettle = new ArrayList<Long>();
		List<Long> loanIds = (List<Long>) loansToSettle.getSerializable(Event.SETTLE_ALL_FILTERED_LOANS.name());
		loanIdsToSettle.addAll(loanIds);
		return databaseAccess.updateStatusByIds(loanIdsToSettle, LoanStatus.SETTLED.getValue());
	}
}
