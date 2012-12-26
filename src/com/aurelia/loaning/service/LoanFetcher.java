package com.aurelia.loaning.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.aurelia.loaning.db.LoanDatabaseAccess;
import com.aurelia.loaning.db.entity.Loan;
import com.aurelia.loaning.domain.EntityToDomainConverter;
import com.aurelia.loaning.domain.Transaction;
import com.aurelia.loaning.domain.TransactionContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.util.LoanUtil;

public class LoanFetcher extends IntentService {

	private LoanDatabaseAccess databaseAccess;
	private EntityToDomainConverter converter;

	public LoanFetcher() {
		super(LoanFetcher.class.getSimpleName());
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		if (converter == null) {
			converter = new EntityToDomainConverter();
		}

		// go fetch loans in database
		if (databaseAccess == null) {
			databaseAccess = new LoanDatabaseAccess(this);
		}
		databaseAccess.open();
		List<Loan> loans = LoanUtil.convert(databaseAccess.selectAllLoans());
		databaseAccess.close();

		// send loans to the view
		Bundle loadedLoansBundle = new Bundle();
		List<Transaction> transactions = new ArrayList<Transaction>();
		Intent loadedLoansFeedback = new Intent(Event.SHOW_LOANINGS.name());

		if (loans != null && !Collections.EMPTY_LIST.equals(loans)) {
			for (Loan loan : loans) {
				transactions.add(converter.convert(loan));
			}
			TransactionContainer transactionContainer = new TransactionContainer();
			transactionContainer.setTransactions(transactions);
			loadedLoansBundle.putSerializable(Event.SHOW_LOANINGS.name(), transactionContainer);
		}

		loadedLoansFeedback.putExtras(loadedLoansBundle);
		sendBroadcast(loadedLoansFeedback);

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
		// TODO : what do we need to return here?
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub

	}
}
