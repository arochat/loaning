package com.aurelia.loaning.domain;

import com.aurelia.loaning.db.entity.Loan;

public class EntityToDomainConverter {

	private final String amountCurrencySeparator = " ";

	public AbstractLoan convert(Loan loanEntity) {

		LoanType loanType = LoanType.valueOf(loanEntity.getType());
		MoneyLoan moneyLoan = null;
		ObjectLoan objectLoan = null;

		switch (loanType) {
		case MONEY_LOAN:
			moneyLoan = new MoneyLoan(loanEntity.getDestination(), loanEntity.getStartDate(), loanEntity.getEndDate(),
					false, loanType);
			setSpecificMoneyLoanData(moneyLoan, loanEntity);
			return moneyLoan;

		case MONEY_BORROWING:
			moneyLoan = new MoneyLoan(loanEntity.getSource(), loanEntity.getStartDate(), loanEntity.getEndDate(),
					false, loanType);
			setSpecificMoneyLoanData(moneyLoan, loanEntity);
			return moneyLoan;

		case OBJECT_LOAN:
			objectLoan = new ObjectLoan(loanEntity.getDestination(), loanEntity.getStartDate(),
					loanEntity.getEndDate(), false, loanType);
			setSpecificObjectLoanData(objectLoan, loanEntity);
			return objectLoan;

		case OBJECT_BORROWING:
			objectLoan = new ObjectLoan(loanEntity.getSource(), loanEntity.getStartDate(), loanEntity.getEndDate(),
					false, loanType);
			setSpecificObjectLoanData(objectLoan, loanEntity);
			return objectLoan;
		default:
			return null;
		}
	}

	private MoneyLoan setSpecificMoneyLoanData(MoneyLoan moneyLoan, Loan loanEntity) {
		moneyLoan.setAmount(computeAmountFromDescription(loanEntity));
		moneyLoan.setCurrency(getCurrencyFromDescription(loanEntity));
		return moneyLoan;
	}

	private ObjectLoan setSpecificObjectLoanData(ObjectLoan objectLoan, Loan loanEntity) {
		objectLoan.setObjectDefinition(loanEntity.getDescription());
		return objectLoan;
	}

	private double computeAmountFromDescription(Loan loan) {
		if (!(LoanType.MONEY_BORROWING.name().equals(loan.getType()) || LoanType.MONEY_LOAN.name().equals(
				loan.getType()))) {
			// TODO : throw exception
		}
		return Double.valueOf(loan.getDescription().split(amountCurrencySeparator)[0]);
	}

	private String getCurrencyFromDescription(Loan loan) {
		if (!(LoanType.MONEY_BORROWING.name().equals(loan.getType()) || LoanType.MONEY_LOAN.name().equals(
				loan.getType()))) {
			// TODO : throw exception
		}
		return loan.getDescription().split(amountCurrencySeparator)[1];
	}
}
