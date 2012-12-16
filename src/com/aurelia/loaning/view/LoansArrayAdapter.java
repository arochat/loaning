package com.aurelia.loaning.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aurelia.loaning.domain.Transaction;

public class LoansArrayAdapter extends ArrayAdapter<Transaction> {

	public LoansArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO when custom display needed
		return null;
	}

}
