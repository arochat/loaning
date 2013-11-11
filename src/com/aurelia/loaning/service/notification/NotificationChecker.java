package com.aurelia.loaning.service.notification;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanFetcher;

public class NotificationChecker extends BroadcastReceiver {

	private LoanFetcher loanFetcher;

	@Override
	public void onReceive(Context context, Intent intent) {

		if (loanFetcher == null) {
			loanFetcher = new LoanFetcher();
		}

		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			fireNotification(context, Event.BOOT_COMPLETED.name());
		}
	}

	public void fireNotification(Context context, String reason) {

		if (loanFetcher == null) {
			loanFetcher = new LoanFetcher();
		}

		Intent notificationIntent = new Intent(context, NotificationService.class);
		// look for loans with notification date in the past
		List<AbstractLoan> elapsedLoans = loanFetcher.getLoansWithNotificationDateInThePast(context);
		if (elapsedLoans.size() == 1) {

			notificationIntent.putExtra(Event.DISPLAY_LOAN_DETAIL.name(), elapsedLoans.get(0));

		} else if (elapsedLoans.size() > 1) {
			Bundle bundle = new Bundle();
			LoansContainer loansContainer = new LoansContainer();
			loansContainer.setLoans(elapsedLoans);
			bundle.putSerializable(Event.LIST_ELAPSED_LOANS.name(), loansContainer);
			notificationIntent.putExtras(bundle);

		}
		String newReason;

		if (Event.APPLICATION_STARTUP.name().equals(reason)) {
			newReason = Event.APPLICATION_STARTUP_RESULT.name();
		} else if (Event.PERIODIC_NOTIFICATION_CHECK.name().equals(reason)) {
			newReason = Event.PERIODIC_NOTIFICATION_CHECK_RESULT.name();
		} else {
			newReason = reason;
		}

		notificationIntent.putExtra("reason", newReason);
		context.startService(notificationIntent);
	}

}
