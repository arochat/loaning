package com.aurelia.loaning.view.actionBar;

import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class OptionsMenuActionBarBuilder implements ActionBarBuilder {

	private Menu menu;
	private Map<String, ActionBarItem> actionBarDefinition;

	// private final int groupId = 1;

	public OptionsMenuActionBarBuilder() {
	}

	public OptionsMenuActionBarBuilder withMenu(Menu menu) {
		this.menu = menu;
		return this;
	}

	public OptionsMenuActionBarBuilder withActionBarDefinition(Map<String, ActionBarItem> actionBarDefinition) {
		this.actionBarDefinition = actionBarDefinition;
		return this;
	}

	public Menu build() {
		Assert.assertNotNull(menu);
		Assert.assertNotNull(actionBarDefinition);

		ActionBarItem item;

		for (Entry<String, ActionBarItem> entry : actionBarDefinition.entrySet()) {

			item = entry.getValue();
			if (item.getIcon() != 0) {

				// menuItem = menu.add(groupId, entry.getKey(), entry.getKey(),
				// item.getTitle());
				// menuItem.setIcon(item.getIcon());
				menu.add(item.getTitle()).setIcon(item.getIcon()).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			} else {
				// menuItem = menu.add(groupId, entry.getKey(), entry.getKey(),
				// item.getTitle());
				menu.add(item.getTitle()).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			}
		}
		// menu.setGroupVisible(groupId, true);
		return menu;
	}

}
