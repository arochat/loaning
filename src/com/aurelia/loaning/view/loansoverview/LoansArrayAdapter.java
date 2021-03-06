package com.aurelia.loaning.view.loansoverview;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelia.loaning.R;
import com.aurelia.loaning.domain.AbstractLoan;

public class LoansArrayAdapter extends BaseAdapter {

	DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");

	private List<AbstractLoan> loans;
	private LayoutInflater inflater;

	public LoansArrayAdapter(Context context, List<AbstractLoan> loansToDisplay) {
		this.inflater = LayoutInflater.from(context);
		this.loans = loansToDisplay;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.loan_item, null);

			viewHolder.sourceAndDestination = (TextView) convertView.findViewById(R.id.item_source_and_destination);
			viewHolder.creationDate = (TextView) convertView.findViewById(R.id.item_creation_date);
			viewHolder.object = (TextView) convertView.findViewById(R.id.item_object);
			viewHolder.loanIcon = (ImageView) convertView.findViewById(R.id.loan_icon);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.sourceAndDestination.setText(loans.get(position).displayPerson());
		viewHolder.creationDate.setText(format.print(loans.get(position).getStartDate()));
		viewHolder.object.setText(loans.get(position).displayDescription());
		viewHolder.loanIcon.setImageResource(loans.get(position).getType().getIcon());

		return convertView;

	}

	@Override
	public int getCount() {
		return loans.size();
	}

	@Override
	public Object getItem(int position) {
		return loans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {

		TextView object;
		TextView creationDate;
		TextView sourceAndDestination;
		ImageView loanIcon;
	}

}
