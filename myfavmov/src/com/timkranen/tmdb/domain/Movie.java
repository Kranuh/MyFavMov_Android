package com.timkranen.tmdb.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie implements Comparable<Movie> {
	@Expose
	private boolean adult;
	@SerializedName("backdrop_path")
	@Expose
	private String backdropPath;
	@Expose
	private int id;
	@SerializedName("original_title")
	@Expose
	private String originalTitle;
	@SerializedName("release_date")
	@Expose
	private String releaseDate;
	@SerializedName("poster_path")
	@Expose
	private String posterPath;
	@Expose
	private double popularity;
	@Expose
	private String title;
	@SerializedName("vote_average")
	@Expose
	private double voteAverage;
	@SerializedName("vote_count")
	@Expose
	private int voteCount;
	
	public boolean isPreview;
	

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	@Override
	public int compareTo(Movie another) {
		if(this.popularity > another.popularity) {
			return -1;
		} else {
			return 1;
		}
	}

}
