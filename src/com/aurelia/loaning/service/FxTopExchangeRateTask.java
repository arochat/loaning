package com.aurelia.loaning.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.aurelia.loaning.domain.MoneyLoan;
import com.google.gson.Gson;

public class FxTopExchangeRateTask implements Callable<Double> {

	private final String baseURL = "http://rate-exchange.appspot.com/currency?";// from=EUR&to=CHF"
	private final NumberFormat formatter = new DecimalFormat("0.00");
	private final String currencyFrom;
	private final String currencyTo;
	private final MoneyLoan loan;

	public FxTopExchangeRateTask(String currencyFrom, String currencyTo, MoneyLoan loan) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.loan = loan;
	}

	@Override
	public Double call() throws Exception {

		if (currencyFrom.equals(currencyTo)) {
			return 1d;
		}

		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		String json = null;
		try {
			String paramUrl = baseURL + "from=" + currencyFrom + "&to=" + currencyTo;
			response = httpclient.execute(new HttpGet(paramUrl));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				json = out.toString();
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			throw new ExecutionException(e);
		} catch (IOException e) {
			throw new ExecutionException(e);
		}
		return convert(new Gson().fromJson(json, ExchangeRate.class).getRate());
	}

	private Double convert(String exchangeRateStr) {
		return Double.valueOf(exchangeRateStr);
	}

	public MoneyLoan getLoan() {
		return loan;
	}

	class ExchangeRate implements Serializable {

		private static final long serialVersionUID = 7367573836720808120L;

		// {"to": "chf", "rate": 1.2363848500000001, "from": "EUR"}
		private String to;
		private String from;
		private String rate;

		private ExchangeRate() {
		}

		private ExchangeRate(String to, String from, String rate) {
			this.to = to;
			this.from = from;
			this.rate = rate;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}

	}

}
