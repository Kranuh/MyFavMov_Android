package com.timkranen.tmdb;

import java.io.IOException;
import java.util.List;

import com.timkranen.tmdb.domain.Movie;

public abstract class SearchTask implements Runnable {
	
	private String query;
	
	public SearchTask(String query) {
		this.query = query;
	}

	@Override
	public void run() {
		//start searching for movies with the given query
		MovService service = new MovService();
		try {
			List<Movie> loadedPreviews = service.queryMovies(query);
			done(loadedPreviews);
		} catch (IOException e) {
			ioExceptionRaised();
		}
	}
	
	public abstract void done(List<Movie> result);
	
	public abstract void ioExceptionRaised();

}
