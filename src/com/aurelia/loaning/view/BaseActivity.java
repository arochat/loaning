package com.aurelia.loaning.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.AbstractActionBarDelegate;
import com.aurelia.loaning.view.loansoverview.StandardLoansOverviewActivity;
import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

public abstract class BaseActivity extends SherlockFragmentActivity implements ActionBar.TabListener,
		OnSlideMenuItemClickListener {

	private SlideMenu slideMenu;
	protected AbstractActionBarDelegate actionBarDelegate;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setupActionBar();
		setSlideMenu();
	}

	// slide menu ----------------------------------------------

	@Override
	public void onSlideMenuItemClick(int itemId) {
		// TODO : real implementation
		switch (itemId) {
		case R.id.item_one:
			backToLoansOverview();
			break;
		case R.id.item_two:
			Toast.makeText(this, "Item two selected", Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_three:
			Toast.makeText(this, "Item three selected", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			slideMenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// action bar ----------------------------------------------------------

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		actionBarDelegate.handleActions(tab, ft, this);
	}

	protected void setupActionBar() {

		final ActionBar ab = createActionBar();
		// setup tabs nav
		actionBarDelegate.setupActionBar(ab, this);

		// default to tab navigation
		showTabsNav();
	}

	protected ActionBar createActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		// set defaults for logo & home up
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		return actionBar;
	}

	protected void showTabsNav() {
		ActionBar ab = getSupportActionBar();
		if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			ab.setDisplayShowTitleEnabled(false);
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		}
	}

	// loans handling
	// -------------------------------------------------------------

	protected void backToLoansOverview() {
		Intent intent = new Intent(this, StandardLoansOverviewActivity.class);
		startActivity(intent);
	}

	private void setSlideMenu() {
		slideMenu = new SlideMenu(this, R.menu.slide, this, 333);
	}

	public void setActionBarDelegate(AbstractActionBarDelegate actionBarDelegate) {
		this.actionBarDelegate = actionBarDelegate;
	}

}
