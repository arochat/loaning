package com.aurelia.loaning.service.notification;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanFetcher;

public class NotificationCheckerService extends IntentService {

	public NotificationCheckerService() {
		super(NotificationCheckerService.class.getSimpleName());
	}

	private LoanFetcher loanFetcher;

	@Override
	protected void onHandleIntent(Intent intent) {

		if (loanFetcher == null) {
			loanFetcher = new LoanFetcher();
		}

		// look for loans with notification date in the past
		List<AbstractLoan> elapsedLoans = loanFetcher.getLoansWithNotificationDateInThePast(this);
		if (elapsedLoans.size() == 1) {
			Intent notificationIntent = new Intent(this, NotificationService.class);
			notificationIntent.putExtra(Event.DISPLAY_LOAN_DETAIL.name(), elapsedLoans.get(0));
			this.startService(notificationIntent);
		} else if (elapsedLoans.size() > 1) {
			Intent notificationIntent = new Intent(this, NotificationService.class);
			Bundle bundle = new Bundle();
			LoansContainer loansContainer = new LoansContainer();
			loansContainer.setLoans(elapsedLoans);
			bundle.putSerializable(Event.LIST_ELAPSED_LOANS.name(), loansContainer);
			notificationIntent.putExtras(bundle);
			this.startService(notificationIntent);
		}

	}

}
