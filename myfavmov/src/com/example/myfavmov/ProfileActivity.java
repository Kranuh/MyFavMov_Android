package com.example.myfavmov;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;

import com.timkranen.adapters.ProfilePagerAdapter;
import com.timkranen.fragments.WatchlistFragment;

public class ProfileActivity extends FragmentActivity implements TabListener {

	private ProfilePagerAdapter pPagerAdapter;
	private ViewPager viewPager;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		pPagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
		viewPager.setAdapter(pPagerAdapter);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle("Your profile");
		createTabs();
		setViewPagerListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	public void navigateToMovieActivity(int movieId) {
		Intent intent = new Intent(ProfileActivity.this, MovieActivity.class);
		intent.putExtra("movie_id", movieId);
		startActivity(intent);
	}

	private void setViewPagerListener() {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void createTabs() {
		actionBar.addTab(actionBar.newTab().setText("Watchlist")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Profile")
				.setTabListener(this));
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
