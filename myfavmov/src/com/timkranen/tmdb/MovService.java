package com.timkranen.tmdb;

import java.io.IOException;
import java.util.List;

import com.timkranen.tmdb.domain.ImdbMovie;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tmdb.domain.TmdbMovie;
import com.timkranen.tmdb.serializer.GsonSerializer;

public class MovService {
	private static String BASE_URL = "http://api.themoviedb.org/3/";
	private static String BASE_BACKDROP_IMAGE_URL = "http://d3gtl9l2a4fn1j.cloudfront.net/t/p/";
	private static String BASE_IMDB_URL = "http://app.imdb.com/title/maindetails?tconst=";
	private static String API_KEY = "2222f3b77f63e633bfa41fba7eb566f3";

	public List<Movie> queryMovies(String query) throws IOException {
		String url = BASE_URL + "search/movie?api_key=" + API_KEY + "&query="
				+ query;
		String returnJson = HttpService.getJson(url);
		List<Movie> foundMovies = GsonSerializer.toMoviePreviewList(returnJson);
		return makePreviewed(foundMovies);
	}
	
	public TmdbMovie getMovie(int movieId) throws IOException {
		String url = BASE_URL + "movie/" + movieId + "?api_key=" + API_KEY;
		String returnJson = HttpService.getJson(url);
		return GsonSerializer.toMovie(returnJson);
	}
	
	public ImdbMovie getImdbMovie(String imdbId) throws IOException {
		String url = BASE_IMDB_URL + imdbId;
		String returnJson = HttpService.getJson(url);
		return GsonSerializer.toImdbMovie(returnJson);
	}
	
	private List<Movie> makePreviewed(List<Movie> movies) {
		for(Movie m : movies) {
			m.isPreview = true;
		}
		return movies;
	}
	
	public static String getMovieBackdrop(String backdropPath) {
		return BASE_BACKDROP_IMAGE_URL + "w300/" + backdropPath;
	}
	
	public static String getPosterBackdrop(String posterPath) {
		return BASE_BACKDROP_IMAGE_URL + "w92" + posterPath;
	}

}
