package com.aurelia.loaning.view.loansoverview;

import java.util.List;

import com.aurelia.loaning.domain.AbstractLoan;

public abstract class AbstractLoansProvider {

	public abstract List<AbstractLoan> getLoansToDisplay();

}
