package com.aurelia.loaning.domain;

import com.aurelia.loaning.db.entity.Loan;

public class DomainToEntityConverter {

	private final String amountCurrencySeparator = " ";
	private final String me = "ME";
	private String source = "";
	private String destination = "";
	private String description = "";

	public Loan convert(AbstractLoan loan) {

		MoneyLoan moneyLoan = null;
		ObjectLoan objectLoan = null;

		switch (loan.getType()) {
		case MONEY_LOAN:
			moneyLoan = (MoneyLoan) loan;
			source = me;
			destination = moneyLoan.getPerson();
			description = computeMoneyLoanDescription(moneyLoan);
			break;

		case MONEY_BORROWING:
			moneyLoan = (MoneyLoan) loan;
			source = moneyLoan.getPerson();
			destination = me;
			description = computeMoneyLoanDescription(moneyLoan);
			break;

		case OBJECT_LOAN:
			objectLoan = (ObjectLoan) loan;
			source = me;
			destination = objectLoan.getPerson();
			description = objectLoan.getObjectDefinition();
			break;

		case OBJECT_BORROWING:
			objectLoan = (ObjectLoan) loan;
			source = objectLoan.getPerson();
			destination = me;
			description = objectLoan.getObjectDefinition();
			break;

		default:
			// TODO : ERROR
		}

		Loan loanEntity = new Loan(source, destination, loan.getStartDate(), loan.getNotificationDate(),
				loan.isContact(), loan.getType().name(), description);

		loanEntity.setId(loan.getId());

		return loanEntity;
	}

	private String computeMoneyLoanDescription(MoneyLoan moneyLoan) {
		return new StringBuilder().append(moneyLoan.getAmount()).append(amountCurrencySeparator)
				.append(moneyLoan.getCurrency()).append(amountCurrencySeparator).append(moneyLoan.getReason())
				.toString();
	}
}
