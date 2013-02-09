package com.aurelia.loaning.view.actionBar;

import java.util.Map;
import java.util.Map.Entry;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;

public class ActionBarBuilder {

	private ActionBar actionBar;

	public ActionBarBuilder(Map<Integer, ActionBarItem> actionBarDefinition, TabListener tabListener,
			ActionBar actionBar) {
		ActionBarItem item;

		for (Entry<Integer, ActionBarItem> entry : actionBarDefinition.entrySet()) {

			item = entry.getValue();

			if (item.getIcon() != 0) {
				actionBar
						.addTab(actionBar.newTab().setText(item.getTitle()).setIcon(item.getIcon())
								.setTabListener(tabListener), entry.getKey(), item.getHighlight());
			} else {
				actionBar.addTab(actionBar.newTab().setText(item.getTitle()).setTabListener(tabListener),
						entry.getKey(), item.getHighlight());
			}
		}

	}

	public ActionBar build() {
		return actionBar;
	}

}
