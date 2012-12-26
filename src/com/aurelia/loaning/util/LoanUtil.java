package com.aurelia.loaning.util;

import java.util.ArrayList;
import java.util.List;

import com.aurelia.loaning.db.entity.Loan;

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

}
