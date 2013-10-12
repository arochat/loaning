package com.aurelia.loaning.view;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.event.Event;
import com.aurelia.loaning.service.LoanSaver;
import com.aurelia.loaning.service.notification.INotificationManager;
import com.aurelia.loaning.service.notification.NotificationManager;
import com.aurelia.loaning.view.actionBar.delegate.DisplayDetailActionBarDelegate;
import com.aurelia.loaning.view.actionBar.delegate.DisplayHistoryDetailActionBarDelegate;
import com.aurelia.loaning.view.loansoverview.LoansHistoryActivity;
import com.aurelia.loaning.view.loansoverview.StandardLoansOverviewActivity;

public class DisplayDetailActivity extends BaseActivity {

	DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");

	private AbstractLoan displayedLoan;
	private BroadcastReceiver dbFeedbackReceiver;
	private IntentFilter intentFilter;
	private IntentFilter intentFilterForHistory;
	private INotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle bundle) {
		this.setActionBarDelegate(new DisplayDetailActionBarDelegate());
		super.onCreate(bundle);
		setContentView(R.layout.loan_detail);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (dbFeedbackReceiver == null) {
			dbFeedbackReceiver = new DbFeedbackReceiver();
			intentFilter = new IntentFilter(Event.LOAN_MODIFIED.name());
			intentFilterForHistory = new IntentFilter(Event.DELETE_LOAN.name());
		}
		registerReceiver(dbFeedbackReceiver, intentFilter);
		registerReceiver(dbFeedbackReceiver, intentFilterForHistory);

		// To get the intent that is sent to the activity. The bundled passed as
		// parameter to onCreate() is the bundle
		// which the Activity uses when it is brought back from a paused state.
		// E.g. if you need to save any state of your
		// activity before it is restarted, this is the place where the stored
		// data is.
		Intent intent = getIntent();
		AbstractLoan loan = null;

		if (intent != null && Event.DISPLAY_LOAN_DETAIL.name().equals(intent.getAction())) {
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.sourceAndDestination = (TextView) findViewById(R.id.loan_source);
			viewHolder.notificationDate = (TextView) findViewById(R.id.loan_reminder_date);
			viewHolder.creationDate = (TextView) findViewById(R.id.loan_creation_date);
			viewHolder.object = (TextView) findViewById(R.id.object_of_loan);

			loan = (AbstractLoan) intent.getExtras().getSerializable(Event.DISPLAY_LOAN_DETAIL.name());

			if (displayedLoan == null) {
				displayedLoan = loan;

				if (loan != null) {
					viewHolder.sourceAndDestination.setText(viewHolder.sourceAndDestination.getText()
							+ loan.displayPerson() + " : ");
					if (loan.getNotificationDate() != null) {
						viewHolder.notificationDate.setText(viewHolder.notificationDate.getText() + " "
								+ format.print(loan.getNotificationDate()));
					} else {
						viewHolder.notificationDate.setText("");
					}
					viewHolder.creationDate.setText(viewHolder.creationDate.getText() + " "
							+ format.print(loan.getStartDate()));
					viewHolder.object.setText(viewHolder.object.getText() + " " + loan.displayDescription());
				}
			}
		}

		if (LoansHistoryActivity.class.getName().equals(intent.getExtras().getSerializable("CALLING_ACTIVITY"))) {
			this.setActionBarDelegate(new DisplayHistoryDetailActionBarDelegate());
		}

		if (Boolean.TRUE.equals(intent.getExtras().get(Event.REMOVE_NOTIFICATION.name()))) {
			if (notificationManager == null) {
				// trick to passe an IntenteService to the NotificationManager
				notificationManager = new NotificationManager(new LoanSaver());
			}
			notificationManager.cancelNotification(intent.getExtras());

			// remove notification from notification bar
			android.app.NotificationManager mNotificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.cancel((int) loan.getId());
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(dbFeedbackReceiver);
	}

	public void deleteLoan(boolean deleteFromHistory) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(Event.DELETE_LOAN.name(), this.displayedLoan);
		bundle.putBoolean("delete_from_history", deleteFromHistory);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(Event.DELETE_LOAN.name());
		intent.putExtras(bundle);
		startService(intent);
	}

	public void settleLoan() {
		Bundle bundle = new Bundle();
		bundle.putSerializable(Event.SETTLE_LOAN.name(), this.displayedLoan);
		Intent intent = new Intent(this, LoanSaver.class);
		intent.setAction(Event.SETTLE_LOAN.name());
		intent.putExtras(bundle);
		startService(intent);
	}

	public AbstractLoan getDisplayedLoan() {
		return displayedLoan;
	}

	private class ViewHolder {

		TextView object;
		TextView notificationDate;
		TextView creationDate;
		TextView sourceAndDestination;
	}

	// ----------------------------------------------------------

	private class DbFeedbackReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Event.LOAN_MODIFIED.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, StandardLoansOverviewActivity.class);
				startActivity(displayLoansIntent);
			} else if (intent != null && Event.DELETE_LOAN.name().equals(intent.getAction())) {
				Intent displayLoansIntent = new Intent(context, LoansHistoryActivity.class);
				startActivity(displayLoansIntent);
			}
		}
	}

}
