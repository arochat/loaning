package com.aurelia.loaning.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.aurelia.loaning.R;
import com.aurelia.loaning.view.actionBar.action.AddLoanCommand;
import com.aurelia.loaning.view.actionBar.delegate.AbstractActionBarDelegate;
import com.aurelia.loaning.view.loansoverview.LoansHistoryActivity;
import com.aurelia.loaning.view.loansoverview.StandardLoansOverviewActivity;
import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

public abstract class BaseActivity extends SherlockFragmentActivity implements OnSlideMenuItemClickListener {

	private SlideMenu slideMenu;
	protected AbstractActionBarDelegate actionBarDelegate;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setSlideMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		setupActionBarMenu(menu);
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	// slide menu ----------------------------------------------

	@Override
	public void onSlideMenuItemClick(int itemId) {
		switch (itemId) {
		case R.id.item_one:
			backToLoansOverview();
			break;
		case R.id.item_two:
			backToLoansHistory();
			break;
		case R.id.item_three:
			backToAddLoanForm();
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			slideMenu.show();
			break;
		default:
			actionBarDelegate.handleActions(item, this);
		}
		return super.onOptionsItemSelected(item);
	}

	// action bar-----------------------------------------------------

	protected void setupActionBarMenu(Menu menu) {

		final ActionBar ab = createActionBar();

		// setup tabs nav
		actionBarDelegate.setupActionBar(menu);
	}

	protected ActionBar createActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		// set defaults for logo & home up
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5C93F0")));
		// actionBar.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#3258A3")));
		return actionBar;
	}

	// loans handling---------------------------------------

	protected void backToLoansOverview() {
		Intent intent = new Intent(this, StandardLoansOverviewActivity.class);
		startActivity(intent);
	}

	private void backToLoansHistory() {
		Intent intent = new Intent(this, LoansHistoryActivity.class);
		startActivity(intent);
	}

	private void backToAddLoanForm() {
		AddLoanCommand command = new AddLoanCommand();
		command.performAction(this);
		// Intent intent = new Intent(this, AddLoanActivity.class);
		// startActivity(intent);
	}

	private void setSlideMenu() {
		slideMenu = new SlideMenu(this, R.menu.slide, this, 333);
	}

	public void setActionBarDelegate(AbstractActionBarDelegate actionBarDelegate) {
		this.actionBarDelegate = actionBarDelegate;
	}

}
