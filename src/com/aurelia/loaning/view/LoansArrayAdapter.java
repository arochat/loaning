package com.aurelia.loaning.view;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.Transaction;

public class LoansArrayAdapter extends BaseAdapter {

	DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");

	private List<Transaction> transactions;
	private LayoutInflater inflater;

	public LoansArrayAdapter(Context context, List<Transaction> transactions) {
		this.inflater = LayoutInflater.from(context);
		this.transactions = transactions;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.loan_item, null);

			viewHolder.destination = (TextView) convertView.findViewById(R.id.item_destination);
			viewHolder.notificationDate = (TextView) convertView.findViewById(R.id.item_notification_date);
			viewHolder.object = (TextView) convertView.findViewById(R.id.item_object);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.destination.setText(transactions.get(position).getDestination());
		viewHolder.notificationDate.setText(format.print(transactions.get(position).getEndDate()));
		viewHolder.object.setText(transactions.get(position).getDescription());
		return convertView;

	}

	@Override
	public int getCount() {
		return transactions.size();
	}

	@Override
	public Object getItem(int position) {
		return transactions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {

		TextView object;
		TextView notificationDate;
		TextView destination;
	}

}
