package com.aurelia.loaning.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.LoanType;
import com.aurelia.loaning.view.AddLoanActivity;

public class AddLoanDialogFragment extends DialogFragment {

	public static final String LOAN_TYPE = "type";

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

						// TODO : make it in a better way
						Intent intent = new Intent(callingActivity, AddLoanActivity.class);
						if (which == 0) {
							intent.putExtra(LOAN_TYPE, LoanType.MONEY_LOAN);
						} else if (which == 1) {
							intent.putExtra(LOAN_TYPE, LoanType.MONEY_BORROWING);
						} else if (which == 2) {
							intent.putExtra(LOAN_TYPE, LoanType.OBJECT_LOAN);
						} else if (which == 3) {
							intent.putExtra(LOAN_TYPE, LoanType.OBJECT_BORROWING);
						} else {
							// TODO : throw exception here
						}
						startActivity(intent);
					}
				});
		return builder.create();
	}
}
