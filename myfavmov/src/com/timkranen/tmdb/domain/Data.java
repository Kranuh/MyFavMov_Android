package com.timkranen.tmdb.domain;

import java.util.List;

public class Data {
	private List<Photo> photos;
	private double rating;
	private List<String> genres;
	private List<Cast> cast_summary;
	private Trailer trailer;
	
	public Trailer getTrailer() {
		return this.trailer;
	}
	
	public boolean hasTrailer() {
		if(this.trailer == null) {
			return false;
		}
		if(this.trailer.getAvailableFormat() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getGenres() {
		String genreString = "";
		for(int i = 0; i < genres.size(); i++) {
			if(i != genres.size() - 1) {
			genreString += genres.get(i) + ", ";
			} else {
				genreString += genres.get(i);
			}
		}
		return genreString;
	}
	
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	
	public List<String> getGenresAsList() {
		return this.genres;
	}
	public List<Cast> getCast_summary() {
		return cast_summary;
	}
	public void setCast_summary(List<Cast> cast_summary) {
		this.cast_summary = cast_summary;
	}
	
	
	
}
