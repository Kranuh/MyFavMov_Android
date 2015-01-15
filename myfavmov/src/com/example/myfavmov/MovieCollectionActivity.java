package com.example.myfavmov;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.timkranen.fragments.MovieCollectionFragment;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tmdb.serializer.GsonSerializer;

public class MovieCollectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_collection);
		initiateFragment();
		getActionBar().setTitle("Search results");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_collection, menu);
		return true;
	}
	
	private void initiateFragment() {
		List<Movie> movies = getMoviesFromIntent();
		FragmentManager fManager = getFragmentManager();
		FragmentTransaction fTransaction = fManager.beginTransaction();
		Fragment newFragment = MovieCollectionFragment.newInstance(movies);
		fTransaction.add(R.id.fragmentContainer_movcollection, newFragment);
		fTransaction.commit();
	}
	
	private List<Movie> getMoviesFromIntent() {
		Intent intent = getIntent();
		String json = intent.getStringExtra("movie_results");
		return GsonSerializer.movieListToJson(json);
	}
	
	public void navigateToMovie(int id) {
		Intent intent = new Intent(this, MovieActivity.class);
		intent.putExtra("movie_id", id);
		startActivity(intent);
	}

}
