package com.aurelia.loaning.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.aurelia.loaning.R;
import com.aurelia.loaning.service.LoanFetcher;
import com.aurelia.loaning.view.dialog.AddLoanDialogFragment;
import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

public abstract class BaseActivity extends SherlockFragmentActivity implements ActionBar.TabListener,
		OnSlideMenuItemClickListener {

	private SlideMenu slideMenu;

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
			Toast.makeText(this, "Item one selected", Toast.LENGTH_SHORT).show();
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
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if ("Add".equals(tab.getText())) {

			// TODO : completely handle fragments
			// stack as in
			// http://www.edumobile.org/android/android-development/fragment-example-in-android/

			// DialogFragment.show() will take care of adding the fragment
			// in a transaction. We also want to remove any currently showing
			// dialog, so make our own transaction and take care of that here.
			ft = getSupportFragmentManager().beginTransaction();
			Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);

			// Create and show the dialog.
			AddLoanDialogFragment addLoanDialogFragment = new AddLoanDialogFragment(this);
			addLoanDialogFragment.show(ft, "dialog");

		} else if ("Overview".equals(tab.getText())) {
			displayListOverview();
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	abstract protected void setupActionBar();

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

	protected void displayListOverview() {
		Intent intent = new Intent(this, LoanFetcher.class);
		startService(intent);
	}

	private void setSlideMenu() {
		slideMenu = new SlideMenu(this, R.menu.slide, this, 333);
	}
}
