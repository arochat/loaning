package com.aurelia.loaning.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.widget.EditText;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.LoansContainer;
import com.aurelia.loaning.view.loansoverview.AbstractLoansOverviewActivity;
import com.aurelia.loaning.view.loansoverview.FilteredLoansOverviewActivity;

public class FilterLoansDialogFragment extends DialogFragment {

	public static final String LOAN_TYPE = "type";

	private Activity callingActivity;

	public FilterLoansDialogFragment(Activity callingActivity) {
		this.callingActivity = callingActivity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final EditText input = new EditText(callingActivity);
		return new AlertDialog.Builder(callingActivity)//
				.setTitle(R.string.filter_loans_fragment_title)//
				.setMessage(R.string.filter_loans_fragment_name_label)//
				.setView(input)//
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Editable value = input.getText();
						Intent intent = new Intent(callingActivity, FilteredLoansOverviewActivity.class);

						Bundle bundle = new Bundle();
						char[] filter = new char[value.length()];
						value.getChars(0, value.length(), filter, 0);
						bundle.putSerializable("filter", filter);

						if (callingActivity instanceof AbstractLoansOverviewActivity) {

							AbstractLoansOverviewActivity loansOverviewActivity = (AbstractLoansOverviewActivity) callingActivity;

							LoansContainer loansContainer = new LoansContainer();
							loansContainer.setLoans(loansOverviewActivity.getLoans());
							bundle.putSerializable("loans", loansContainer);

						}
						intent.putExtras(bundle);
						startActivity(intent);
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Do nothing.
					}
				}).create();

	}
}
