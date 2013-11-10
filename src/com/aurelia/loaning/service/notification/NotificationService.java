package com.aurelia.loaning.service.notification;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.view.DisplayDetailActivity;
import com.aurelia.loaning.view.loansoverview.ElapsedLoansOverviewActivity;

public class NotificationService extends IntentService {

	public NotificationService() {
		super(NotificationService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		AbstractLoan loan = (AbstractLoan) intent.getExtras().getSerializable(Event.DISPLAY_LOAN_DETAIL.name());
		if (loan != null) {
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)//
					.setSmallIcon(R.drawable.icon_cash)//
					.setContentTitle("Loaning reminder")//
					.setContentText(loan.displayNotification());

			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(this, DisplayDetailActivity.class);
			resultIntent.setAction(Event.DISPLAY_LOAN_DETAIL.name());
			resultIntent.putExtra(Event.DISPLAY_LOAN_DETAIL.name(),
					intent.getExtras().getSerializable(Event.DISPLAY_LOAN_DETAIL.name()));
			resultIntent.putExtra(Event.REMOVE_NOTIFICATION.name(), Boolean.TRUE);
			resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);

			sendNotification(resultIntent, mBuilder, loan.getId());

		} else {
			LoansContainer loansContainer = (LoansContainer) intent.getExtras().getSerializable(
					Event.LIST_ELAPSED_LOANS.name());

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)//
					.setSmallIcon(R.drawable.icon_cash)//
					.setContentTitle("Loaning reminder")//
					.setContentText("You have several elapsed loans")//
					.setAutoCancel(true);

			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(this, ElapsedLoansOverviewActivity.class);
			resultIntent.setAction(Event.LIST_ELAPSED_LOANS.name());

			Bundle bundle = new Bundle();
			bundle.putSerializable(Event.LIST_ELAPSED_LOANS.name(), loansContainer);
			resultIntent.putExtras(bundle);
			resultIntent.putExtra(Event.LIST_ELAPSED_LOANS.name(),
					intent.getExtras().getSerializable(Event.LIST_ELAPSED_LOANS.name()));
			resultIntent.putExtra(Event.REMOVE_NOTIFICATION.name(), Boolean.TRUE);
			resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			sendNotification(resultIntent, mBuilder, 0);

		}
		return START_NOT_STICKY;
	}

	private void sendNotification(Intent resultIntent, NotificationCompat.Builder mBuilder, long notificationId) {
		// The stack builder object will contain an artificial back stack for the started Activity.
		// This ensures that navigating backward from the Activity leads out of your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.from(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(DisplayDetailActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);

		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		android.app.NotificationManager mNotificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// in this case, no need for unique ID for the notification
		mNotificationManager.notify((int) notificationId, mBuilder.getNotification());

	}
}
