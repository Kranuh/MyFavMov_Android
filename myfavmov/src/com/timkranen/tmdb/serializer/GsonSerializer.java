package com.timkranen.tmdb.serializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.timkranen.tmdb.domain.ImdbMovie;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tmdb.domain.ResultsPage;
import com.timkranen.tmdb.domain.Statistic;
import com.timkranen.tmdb.domain.TmdbMovie;

public class GsonSerializer {

	public static TmdbMovie toMovie(String json) {
		Gson gson = new Gson();
		return (TmdbMovie) gson.fromJson(json, TmdbMovie.class);
	}
	
	public static String fullMovieToJson(TmdbMovie m) {
		Gson gson = new Gson();
		return gson.toJson(m, TmdbMovie.class);
	}
	
	public static ImdbMovie toImdbMovie(String json) {
		Gson gson = new Gson();
		return (ImdbMovie)gson.fromJson(json, ImdbMovie.class);
	}
	
	public static String fromImdbMovie(ImdbMovie imdbMovie) {
		Gson gson = new Gson();
		return gson.toJson(imdbMovie, ImdbMovie.class);
	}

	public static List<Movie> toMoviePreviewList(String json) {
		Gson gson = new Gson();
		ResultsPage list = null;
		try {
			list = gson.fromJson(json, ResultsPage.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return new ArrayList<Movie>();
		}
		return list.getResults();

	}
	
	public static List<Movie> movieListToJson(String json) {
		Gson gson = new Gson();
		Type listOfMovies = new TypeToken<List<Movie>>(){}.getType();
		List<Movie> movies = gson.fromJson(json, listOfMovies);
		return movies;
	}
	
	public static Statistic jsonToStatistic(String json) {
		Gson gson = new Gson();
		Statistic stat = (Statistic) gson.fromJson(json, Statistic.class);
		return stat;
	}
	
	public static String StatisticToJson(Statistic s) {
		Gson gson = new Gson();
		String json = gson.toJson(s, Statistic.class);
		return json;
	}

	public static String fromMovieList(List<Movie> movieList) {
		Gson gson = new Gson();
		Type listOfMovies = new TypeToken<List<Movie>>(){}.getType();
		String json = gson.toJson(movieList, listOfMovies);
		return json;
	}

	public static Gson getGson() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson;
	}
}
