package com.aurelia.loaning.domain;

import com.aurelia.loaning.R;

public enum LoanType {

	// TODO : put correct icons here
	MONEY_LOAN(R.drawable.icon_cash), MONEY_BORROWING(R.drawable.icon_cash), OBJECT_LOAN(R.drawable.icon_balance), OBJECT_BORROWING(
			R.drawable.icon_balance);

	private int icon;

	private LoanType(int icon) {
		this.icon = icon;
	}

	public int getIcon() {
		return icon;
	}

}
