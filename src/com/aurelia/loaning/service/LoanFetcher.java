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
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.AbstractLoan.LoanStatus;
import com.aurelia.loaning.domain.EntityToDomainConverter;
import com.aurelia.loaning.domain.LoansContainer;
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

		// go fetch active loans in database
		if (databaseAccess == null) {
			databaseAccess = new LoanDatabaseAccess(this);
		}
		Intent intentToSend;
		if (LoanStatus.SETTLED.name().equals(intent.getStringExtra("status"))) {
			intentToSend = fetchLoans(LoanStatus.SETTLED, Event.SHOW_LOANINGS_HISTORY);
		} else {
			intentToSend = fetchLoans(LoanStatus.ACTIVE, Event.SHOW_LOANINGS);
		}
		sendBroadcast(intentToSend);

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
		// TODO : what do we need to return here?
	}

	public List<AbstractLoan> getLoansWithNotificationDateInThePast() {
		List<AbstractLoan> loans;
		List<Loan> loansFromDB = LoanUtil.convert(databaseAccess.getLoansWithNotificationDateInThePast());
		if (loansFromDB != null && !Collections.EMPTY_LIST.equals(loansFromDB)) {
			loans = new ArrayList<AbstractLoan>(loansFromDB.size());
			for (Loan loanFromDB : loansFromDB) {
				loans.add(converter.convert(loanFromDB));
			}
			return loans;
		}
		return Collections.EMPTY_LIST;

	}

	private Intent fetchLoans(LoanStatus loanStatus, Event event) {
		databaseAccess.open();
		List<Loan> loansFromDB = LoanUtil.convert(databaseAccess.selectLoansWithStatus(loanStatus.getValue()));

		databaseAccess.close();

		// send loans to the view
		Bundle loadedLoansBundle = new Bundle();
		List<AbstractLoan> loans = new ArrayList<AbstractLoan>();
		Intent loadedLoansFeedback = new Intent(event.name());

		if (loansFromDB != null && !Collections.EMPTY_LIST.equals(loansFromDB)) {
			for (Loan loanFromDB : loansFromDB) {
				loans.add(converter.convert(loanFromDB));
			}
			LoansContainer transactionContainer = new LoansContainer();
			transactionContainer.setLoans(loans);
			loadedLoansBundle.putSerializable(event.name(), transactionContainer);
		}

		loadedLoansFeedback.putExtras(loadedLoansBundle);
		return loadedLoansFeedback;
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub

	}
}
