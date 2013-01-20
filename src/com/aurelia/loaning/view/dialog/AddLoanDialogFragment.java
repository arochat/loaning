package com.aurelia.loaning.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.aurelia.loaning.R;
import com.aurelia.loaning.view.AddLoanActivity;

public class AddLoanDialogFragment extends DialogFragment {

	private Activity callingActivity;

	public AddLoanDialogFragment(Activity callingActivity) {
		this.callingActivity = callingActivity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.add_loan_fragment_title).setItems(R.array.loan_choice,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index position
						// of the selected item
						Intent intent = new Intent(callingActivity, AddLoanActivity.class);
						startActivity(intent);
					}
				});
		return builder.create();
	}
}
