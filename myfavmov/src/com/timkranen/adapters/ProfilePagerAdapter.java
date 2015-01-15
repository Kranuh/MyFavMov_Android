package com.timkranen.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.timkranen.fragments.ProfileFragment;
import com.timkranen.fragments.WatchlistFragment;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

	public ProfilePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		if(arg0 == 0) {
			Fragment fragment = new WatchlistFragment();
			return fragment;
		} else {
			Fragment fragment = new ProfileFragment();
			return fragment;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(position == 0) {
			return "Watchlist";
		} else {
			return "Profile";
		}
	}

}
