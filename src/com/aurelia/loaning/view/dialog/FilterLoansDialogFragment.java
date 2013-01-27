package com.aurelia.loaning.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.aurelia.loaning.R;

public class FilterLoansDialogFragment extends DialogFragment {

	public static final String LOAN_TYPE = "type";

	private Activity callingActivity;

	public FilterLoansDialogFragment(Activity callingActivity) {
		this.callingActivity = callingActivity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// LayoutInflater factory = LayoutInflater.from(this);
		// final View textEntryView =
		// factory.inflate(R.layout.alert_dialog_text_entry, null);

		// final View textEntryView =
		// callingActivity.findViewById(R.id.filter_field);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.filter_loans_fragment_title)//
				.setView(R.layout.filter_loans).//
				setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						/* User clicked OK so do some stuff */
					}
				}).create();
		return builder.create();
		//
		// LayoutInflater factory = LayoutInflater.from(this);
		// final View textEntryView =
		// factory.inflate(R.layout.alert_dialog_text_entry, null);
		// return new
		// AlertDialog.Builder(AlertDialogSamples.this).setIcon(R.drawable.alert_dialog_icon)
		// .setTitle(R.string.alert_dialog_text_entry).setView(textEntryView)
		// .setPositiveButton(R.string.alert_dialog_ok, new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int whichButton) {
		//
		// /* User clicked OK so do some stuff */
		// }
		// }).create();
	}
}
