package com.example.myfavmov;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.timkranen.fragments.MovieFragment;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tools.LocalStorageTool;

public class MovieActivity extends Activity {

	int movieId;

	private Movie localMovie = new Movie();
	private boolean localMovieIsSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		getMovie();
		initiateFragment();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
		
		//modify the search interface background
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		searchView.setBackgroundColor(Color.argb(47, 247, 118, 13)); //this is a color estimate because the actionbar is actually a gradient
		
		if (localMovieIsSet) {
			LocalStorageTool lTool = new LocalStorageTool(
					getApplicationContext());
			if (!lTool.isInWatchlist(localMovie)) {
				menu.getItem(1).setVisible(true); //1 == add to watchlist
				menu.getItem(2).setVisible(false); //2 == delete from watchlist
			} else {
				menu.getItem(1).setVisible(false);
				menu.getItem(2).setVisible(true);
			}
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_to_watchlist:
			addMovieToWatchlist();
			break;

		case R.id.delete_from_watchlist:
			deleteMovieFromWatchlist();
			break;
		case R.id.action_search:
			onSearchRequested();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setLocalMovie(String movieTitle, String releaseDate,
			String backdropUrl) {
		this.localMovie.setTitle(movieTitle);
		this.localMovie.setReleaseDate(releaseDate);
		this.localMovie.setBackdropPath(backdropUrl);
		this.localMovie.setId(this.movieId);
		this.localMovieIsSet = true;
	}

	private void addMovieToWatchlist() {
		LocalStorageTool sTool = new LocalStorageTool(getApplicationContext());
		sTool.addToWatchlist(this.localMovie);
		this.invalidateOptions();
	}

	private void deleteMovieFromWatchlist() {
		LocalStorageTool sTool = new LocalStorageTool(getApplicationContext());
		sTool.deleteFromWatchlist(this.localMovie);
		this.invalidateOptions();
	}

	private void initiateFragment() {
		FragmentManager fManager = getFragmentManager();
		FragmentTransaction fTransaction = fManager.beginTransaction();
		Fragment newFragment = MovieFragment.newInstance(this.movieId);
		fTransaction.add(R.id.fragmentContainer_movfragment, newFragment);
		fTransaction.commit();
	}

	private void getMovie() {
		Intent intent = getIntent();
		this.movieId = intent.getIntExtra("movie_id", 0);
	}

	public void setActionBarTitle(String title) {
		getActionBar().setTitle(title);
	}

	public void invalidateOptions() {
		this.invalidateOptionsMenu();
	}

}
