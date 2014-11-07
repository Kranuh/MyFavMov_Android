package com.timkranen.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tmdb.serializer.GsonSerializer;

public class LocalStorageTool {
	private Context appContext;
	private String PREF_NAME = "myfavmov.watchlist";
	private SharedPreferences preferences;

	/*
	 * Handles saving and retrieving watchlist, context has to be app context!
	 */
	public LocalStorageTool(Context appContext) {
		this.appContext = appContext;
		preferences = appContext.getSharedPreferences(PREF_NAME, 0);
	}

	public void addToWatchlist(Movie m) {
		List<Movie> currentWatchlist = getWatchlist();
		currentWatchlist.add(m);
		SharedPreferences.Editor editor = preferences.edit();
		String json = GsonSerializer.fromMovieList(currentWatchlist);
		if (preferences.getString("watchlist", "") != null
				|| !preferences.getString("watchlist", "").isEmpty()) {
			editor.remove("watchlist");
		}
		editor.putString("watchlist", json);
		editor.commit();
	}

	public List<Movie> getWatchlist() {
		String json = preferences.getString("watchlist", "");
		if (!json.isEmpty()) {
			List<Movie> watchlist = GsonSerializer.movieListToJson(json);
			return watchlist;
		} else {
			return new ArrayList<Movie>();
		}
	}
	
	public void deleteFromWatchlist(Movie m) {
		List<Movie> watchlist = getWatchlist();
		
		int loc = -1;
		for(int i = 0; i < watchlist.size(); i++) {
			if(watchlist.get(i).getId() == m.getId()) {
				loc = i;
			}
		}
		
		if(loc != -1) {
			watchlist.remove(loc);
		}
		
		SharedPreferences.Editor editor = preferences.edit();
		String json = GsonSerializer.fromMovieList(watchlist);
		if (preferences.getString("watchlist", "") != null
				|| !preferences.getString("watchlist", "").isEmpty()) {
			editor.remove("watchlist");
		}
		editor.putString("watchlist", json);
		editor.commit();
		
	}

	public boolean isInWatchlist(Movie m) {
		List<Movie> watchlist = getWatchlist();
		for (Movie movie : watchlist) {
			if (movie.getId() == m.getId()) {
				return true;
			}
		}
		return false;
	}
}
