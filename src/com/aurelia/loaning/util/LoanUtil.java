package com.aurelia.loaning.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.aurelia.loaning.db.entity.Loan;
import com.aurelia.loaning.domain.AbstractLoan;
import com.aurelia.loaning.view.BaseActivity;

public class LoanUtil {

	public static List<Loan> convert(List<Object> objects) {

		List<Loan> loans = new ArrayList<Loan>();

		for (Object object : objects) {
			if (object instanceof Loan) {
				loans.add((Loan) object);
			}
		}
		return loans;
	}

	public static Intent createIntentWithBundledLoan(BaseActivity from, Class to, AbstractLoan loan) {
		Intent intent = new Intent(from, to);
		Bundle bundle = new Bundle();
		bundle.putSerializable("loan", loan);
		intent.putExtras(bundle);
		return intent;
	}

}
