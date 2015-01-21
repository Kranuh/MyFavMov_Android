package com.timkranen.tmdb;

import java.io.IOException;
import java.util.List;

import android.app.Activity;

import com.timkranen.tmdb.domain.Movie;

public abstract class SearchTask implements Runnable {
	
	private String query;
	private Activity activity;
	
	public SearchTask(String query, Activity activity) {
		this.query = query;
		this.activity = activity;
	}

	@Override
	public void run() {
		//start searching for movies with the given query
		MovService service = new MovService();
		try {
			List<Movie> loadedPreviews = service.queryMovies(query);
			done(loadedPreviews, this.activity);
		} catch (IOException e) {
			ioExceptionRaised();
		}
	}
	
	public abstract void done(List<Movie> result, Activity activity);
	
	public abstract void ioExceptionRaised();

}
