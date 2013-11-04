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
			// look for loans with notification date in the past
			List<AbstractLoan> elapsedLoans = loanFetcher.getLoansWithNotificationDateInThePast(context);
			if (elapsedLoans.size() == 1) {
				Intent notificationIntent = new Intent(context, NotificationService.class);
				notificationIntent.putExtra(Event.DISPLAY_LOAN_DETAIL.name(), elapsedLoans.get(0));
				context.startService(notificationIntent);
			} else if (elapsedLoans.size() > 1) {
				Intent notificationIntent = new Intent(context, NotificationService.class);
				Bundle bundle = new Bundle();
				LoansContainer loansContainer = new LoansContainer();
				loansContainer.setLoans(elapsedLoans);
				bundle.putSerializable(Event.LIST_ELAPSED_LOANS.name(), loansContainer);
				notificationIntent.putExtras(bundle);
				context.startService(notificationIntent);
			}
		}

	}

}
