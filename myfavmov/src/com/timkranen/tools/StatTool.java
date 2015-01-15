package com.timkranen.tools;

import android.content.Context;

import com.timkranen.tmdb.domain.ImdbMovie;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tmdb.domain.Statistic;

public class StatTool {
	
	private Context appContext;
	
	/*
	 * Need app context for local storage tool
	 */
	public StatTool(Context appContext) {
		this.appContext = appContext;
	}
	
	/*
	 * Update  the statistics (or create new stats) using the movie
	 * and imdbmovie that is about to be deleted
	 */
	public void updateStatistics(Movie m, ImdbMovie im) {
		Statistic current = getStatistics();
		current.addData(im, m.getReleaseDate());
		saveStatistics(current);
	}
	
	private void saveStatistics(Statistic s) {
		LocalStorageTool lsTool = new LocalStorageTool(appContext);
		lsTool.saveStatistics(s);
	}
	
	private Statistic getStatistics() {
		LocalStorageTool lsTool = new LocalStorageTool(appContext);
		return lsTool.getStatistics();
	}
}
