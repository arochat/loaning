package com.aurelia.loaning.view.actionBar;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public abstract class ActionBarItem {

	String label;
	String title;
	int icon;
	boolean highlight;

	public ActionBarItem(String label, String title, int icon, boolean highlight) {
		super();
		this.label = label;
		this.title = title;
		this.icon = icon;
		this.highlight = highlight;
	}

	abstract public void action(FragmentTransaction ft, SherlockFragmentActivity activity);

	public String getLabel() {
		return label;
	}

	public String getTitle() {
		return title;
	}

	public int getIcon() {
		return icon;
	}

	public boolean getHighlight() {
		return highlight;
	}

}
