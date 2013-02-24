package com.aurelia.loaning.view.actionBar;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.aurelia.loaning.view.actionBar.action.ActionBarCommand;

public class ActionBarItem {

	private String label;
	private String title;
	private int icon;
	private boolean highlight;
	private ActionBarCommand action;

	public ActionBarItem(String label, String title, int icon, boolean highlight, ActionBarCommand action) {
		super();
		this.label = label;
		this.title = title;
		this.icon = icon;
		this.highlight = highlight;
		this.action = action;
	}

	public void action(/* FragmentTransaction ft, */SherlockFragmentActivity activity) {
		action.performAction(/* ft, */activity);
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

	public ActionBarCommand getAction() {
		return action;
	}

}
