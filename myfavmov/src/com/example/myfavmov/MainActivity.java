package com.example.myfavmov;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.timkranen.fragments.SearchFragment;
import com.timkranen.tmdb.SearchTask;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tmdb.serializer.GsonSerializer;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		if (!handleSearch()) {
			setupStartFragment();
		}
	}

	private boolean handleSearch() {
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchTask sTask = new SearchTask(query, this) {
				
				@Override
				public void ioExceptionRaised() {
					//TODO
				}
				
				@Override
				public void done(List<Movie> result, Activity activity) {
					navigateToMovCollectionActivity(result);
					finish();
				}
			};
			
			Thread sThread = new Thread(sTask);
			sThread.start();
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setupStartFragment() {
		FragmentManager fManager = getFragmentManager();
		FragmentTransaction fTransaction = fManager.beginTransaction();
		Fragment newFragment = new SearchFragment();
		fTransaction.replace(R.id.fragmentContainer, newFragment);
		fTransaction.commit();
	}

	public void navigateToMovCollectionActivity(List<Movie> results) {
		Intent intent = new Intent(MainActivity.this,
				MovieCollectionActivity.class);
		intent.putExtra("movie_results", GsonSerializer.fromMovieList(results));
		startActivity(intent);
	}

	public void navigateToProfileActivity() {
		Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
