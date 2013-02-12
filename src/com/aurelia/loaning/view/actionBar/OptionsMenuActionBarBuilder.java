package com.aurelia.loaning.view.actionBar;

import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class OptionsMenuActionBarBuilder implements ActionBarBuilder {

	private Menu menu;
	private Map<Integer, ActionBarItem> actionBarDefinition;

	public OptionsMenuActionBarBuilder() {
	}

	public OptionsMenuActionBarBuilder withMenu(Menu menu) {
		this.menu = menu;
		return this;
	}

	public OptionsMenuActionBarBuilder withActionBarDefinition(Map<Integer, ActionBarItem> actionBarDefinition) {
		this.actionBarDefinition = actionBarDefinition;
		return this;
	}

	public Menu build() {
		Assert.assertNotNull(menu);
		Assert.assertNotNull(actionBarDefinition);
		// menu.add("Save").setIcon(R.drawable.ic_compose).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		ActionBarItem item;

		for (Entry<Integer, ActionBarItem> entry : actionBarDefinition.entrySet()) {

			item = entry.getValue();

			if (item.getIcon() != 0) {
				menu.add(item.getTitle()).setIcon(item.getIcon()).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

				// actionBar
				// .addTab(actionBar.newTab().setText(item.getTitle()).setIcon(item.getIcon())
				// .setTabListener(tabListener), entry.getKey(),
				// item.getHighlight());
			} else {
				menu.add(item.getTitle()).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				// actionBar.addTab(actionBar.newTab().setText(item.getTitle()).setTabListener(tabListener),
				// entry.getKey(), item.getHighlight());
			}
		}

		return menu;
	}

}
