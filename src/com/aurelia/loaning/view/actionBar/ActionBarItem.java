package com.aurelia.loaning.view.actionBar;

import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.actionBar.action.ActionBarAction;

public class ActionBarItem {

	private String label;
	private String title;
	private int icon;
	private boolean highlight;
	private ActionBarAction action;

	public ActionBarItem(String label, String title, int icon, boolean highlight, ActionBarAction action) {
		super();
		this.label = label;
		this.title = title;
		this.icon = icon;
		this.highlight = highlight;
		this.action = action;
	}

	public void action(FragmentTransaction ft, SherlockFragmentActivity activity) {
		action.performAction(ft, activity);
	}

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

	public ActionBarAction getAction() {
		return action;
	}

}
