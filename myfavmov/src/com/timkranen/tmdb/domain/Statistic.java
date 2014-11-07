package com.timkranen.tmdb.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.timkranen.tools.GenreComparator;

public class Statistic {
	private int watchCount; // number of movies 'watched'
	private List<String> allGenres; // all genres that have been 'watched'
	private List<Integer> allReleaseDates; // all release dates
	private double communalRating;
	
	public void setWatchCount(int watchCount) {
		this.watchCount = watchCount;
	}
	
	public int getWatchCount()	{
		return this.watchCount;
	}
	
	public void setAllGenres(List<String> allGenres) {
		this.allGenres = allGenres;
	}

	public void setAllReleaseDates(List<Integer> allReleaseDates) {
		this.allReleaseDates = allReleaseDates;
	}

	public void setCommunalRating(double communalRating) {
		this.communalRating = communalRating;
	}

	/*
	 * Adds a movie to the statistics data set, this happens when a movie is
	 * watched. We need the IMDBmovie because we want to create statistics
	 * regarding genres
	 * 
	 * @param m ImdbMovie object
	 * 
	 * @param releaseDate The releasedate, this has to come from the TmdbMovie
	 * database, it's empty if there is no releasedate
	 */
	public void addData(ImdbMovie m, String releaseDate) {
		//increment watchcount
		watchCount++;
		
		// set genres
		List<String> genres = m.getData().getGenresAsList();
		allGenres.addAll(genres);

		// add release date in proper manner
		if (!releaseDate.isEmpty()) {
			String year = releaseDate.substring(0, 3);
			try {
				int numYear = Integer.parseInt(year);
				allReleaseDates.add(numYear);
			} catch (NumberFormatException e) {
				// just dont add it
			}
		}
		
		//add the rating to communalrating
		communalRating += m.getData().getRating();
	}

	public double getAvgRating() {
		return communalRating / watchCount;
	}
	
	public Map<String, Integer> getFavGenres() {
		//create occurency map
		Set<String> uniqueOcc = new HashSet<String>(this.allGenres);
		Map<String, Integer> occMap = new HashMap<String, Integer>(); //string is genre name, integer is occurency
		for(String key : uniqueOcc) {
			occMap.put(key, Collections.frequency(this.allGenres, key));
		}
		
		//sort the occmap by value
		GenreComparator gc = new GenreComparator(occMap);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(gc);
		sortedMap.putAll(occMap);
		
		//return the first three of the treemap
		int i = 0;
		Map<String, Integer> topMap = new HashMap<String, Integer>();
		for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			if(i <= 2) {
				topMap.put(entry.getKey(), entry.getValue());
			} else {
				break;
			}
		}
		
		return topMap;
	}
	
	public int getAvgReleaseDate() {
		int totalDate = 0;
		for(Integer i : allReleaseDates) {
			totalDate += allReleaseDates.get(i);
		}
		
		return totalDate / allReleaseDates.size();
	}
}
