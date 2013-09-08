package com.aurelia.loaning.util;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public enum Currency {

	CHF, USD, EUR;

	public String getValue() {
		return this.name();
	}

	public static Collection<String> getPrintableValues() {
		return Collections2.transform(Arrays.asList(values()), new Function<Currency, String>() {

			@Override
			public String apply(Currency currency) {
				return currency.name();
			}

		});
	}

}
