package com.timkranen.tmdb.domain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.timkranen.tools.GenreComparator;

public class Statistic {
	private int watchCount; // number of movies 'watched'
	private List<String> allGenres = new ArrayList<String>(); // all genres that have been 'watched'
	private List<Integer> allReleaseDates = new ArrayList<Integer>(); // all release dates
	private double communalRating;
	
	/*
	 * no args for gson don't use
	 */
	public Statistic() {
	}
	
	public boolean isFilled() {
		return allGenres.size() > 0;
	}
	
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
			String year = releaseDate.substring(0, 4);
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
		Double avg = communalRating / watchCount;
		
		DecimalFormat dFormat = new DecimalFormat("#.#");
		DecimalFormatSymbols dFSymbols = new DecimalFormatSymbols();
		dFSymbols.setDecimalSeparator('.');
		dFormat.setDecimalFormatSymbols(dFSymbols);
		double returnValue = Double.valueOf(dFormat.format(avg));
		return returnValue;
	}
	
	public List<Map.Entry<String, Integer>> getFavGenres() { //bad practice but its doable because the list is super tiny
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
		List<Map.Entry<String, Integer>> topList = new ArrayList<Map.Entry<String, Integer>>();
		int i = 0;
		for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			if(i <= 2) {
				topList.add(i, entry);
				i++;
			} else {
				break;
			}
		}
		
		
		
		return topList;
	}
	
	public int getAvgReleaseDate() {
		int totalDate = 0;
		for(int i = 0; i < allReleaseDates.size(); i++) {
			totalDate += allReleaseDates.get(i);
		}
		
		if(allReleaseDates.size() != 0) {
		return totalDate / allReleaseDates.size();
		} else {
			return 1;
		}
	}
}
