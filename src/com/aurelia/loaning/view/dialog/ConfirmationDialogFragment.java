package com.aurelia.loaning.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.aurelia.loaning.view.actionBar.action.ActionBarCommandWithConfirmation;

public class ConfirmationDialogFragment extends DialogFragment {

	private final Activity callingActivity;
	private final ActionBarCommandWithConfirmation command;

	public ConfirmationDialogFragment(Activity callingActivity, ActionBarCommandWithConfirmation command) {
		this.callingActivity = callingActivity;
		this.command = command;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = callingActivity.getLayoutInflater();

		return new AlertDialog.Builder(callingActivity)//
				// .setView(inflater.inflate(R.layout.delete_loan_dialog,
				// null))//
				.setMessage(command.getConfirmationMessage())//
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// juste close the popup
					}
				}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						command.performActionCallback(callingActivity);
					}
				}).create();
	}
}
