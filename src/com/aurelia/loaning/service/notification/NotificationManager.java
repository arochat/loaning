package com.aurelia.loaning.service.notification;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
import com.aurelia.loaning.util.LoanUtil;

public class NotificationManager implements INotificationManager {

	private final IntentService callingService;

	public NotificationManager(IntentService callingService) {
		this.callingService = callingService;
	}

	@Override
	public void addNotification(Bundle bundle) {
		AbstractLoan loan = (AbstractLoan) bundle.getSerializable(Event.SAVE_LOANING.name());
		if (loan != null && loan.getNotificationDate() != null) {
			scheduleNotification(loan);
		}
	}

	@Override
	public void updateNotification(Bundle bundle) {
		AbstractLoan loan = (AbstractLoan) bundle.getSerializable(Event.UPDATE_LOAN.name());
		if (loan != null && loan.getNotificationDate() != null) {
			cancelNotification(loan);
			scheduleNotification(loan);
		}

	}

	@Override
	public void cancelNotification(Bundle bundle) {
		AbstractLoan loan = (AbstractLoan) bundle.getSerializable(Event.SETTLE_LOAN.name());
		if (loan != null && loan.getNotificationDate() != null) {
			cancelNotification(loan);
		}
	}

	@Override
	public void cancelAllNotifications(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	public PendingIntent createPendingIntent(AbstractLoan loan) {
		Intent intent = new Intent(callingService, NotificationService.class);
		intent.putExtra(Event.DISPLAY_LOAN_DETAIL.name(), loan);
		return PendingIntent.getService(callingService, LoanUtil.safeLongToInt(loan.getId()), intent,
				PendingIntent.FLAG_ONE_SHOT);
	}

	private void cancelNotification(AbstractLoan loan) {
		AlarmManager alarmManager = (AlarmManager) callingService.getSystemService(LoanSaver.ALARM_SERVICE);
		alarmManager.cancel(createPendingIntent(loan));
	}

	private void scheduleNotification(AbstractLoan loan) {
		AlarmManager alarmManager = (AlarmManager) callingService.getSystemService(LoanSaver.ALARM_SERVICE);
		long notificationTime = loan.getNotificationDate().getMillis();
		alarmManager.set(AlarmManager.RTC, notificationTime, createPendingIntent(loan));
	}

}
