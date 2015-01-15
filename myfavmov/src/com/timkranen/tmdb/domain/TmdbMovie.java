package com.timkranen.tmdb.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TmdbMovie {
	@Expose
	private Boolean adult;
	@SerializedName("backdrop_path")
	@Expose
	private String backdropPath;
	@SerializedName("belongs_to_collection")
	@Expose
	private Object belongsToCollection;
	@Expose
	private Integer budget;
	@Expose
	private List<Object> genres = new ArrayList<Object>();
	@Expose
	private String homepage;
	@Expose
	private Integer id;
	@SerializedName("imdb_id")
	@Expose
	private String imdbId;
	@SerializedName("original_title")
	@Expose
	private String originalTitle;
	@Expose
	private String overview;
	@Expose
	private Double popularity;
	@SerializedName("poster_path")
	@Expose
	private String posterPath;
	@SerializedName("production_companies")
	@Expose
	private List<Object> productionCompanies = new ArrayList<Object>();
	@SerializedName("production_countries")
	@Expose
	private List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();
	@SerializedName("release_date")
	@Expose
	private String releaseDate;
	@Expose
	private Integer revenue;
	@Expose
	private Integer runtime;
	@SerializedName("spoken_languages")
	@Expose
	private List<SpokenLanguage> spokenLanguages = new ArrayList<SpokenLanguage>();
	@Expose
	private String status;
	@Expose
	private String tagline;
	@Expose
	private String title;
	@SerializedName("vote_average")
	@Expose
	private Double voteAverage;
	@SerializedName("vote_count")
	@Expose
	private Integer voteCount;

	public Boolean getAdult() {
		return adult;
	}

	public void setAdult(Boolean adult) {
		this.adult = adult;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public Object getBelongsToCollection() {
		return belongsToCollection;
	}

	public void setBelongsToCollection(Object belongsToCollection) {
		this.belongsToCollection = belongsToCollection;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public List<Object> getGenres() {
		return genres;
	}

	public void setGenres(List<Object> genres) {
		this.genres = genres;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getOverview() {
		if(this.overview == null || this.overview.isEmpty()) {
			return "N/A";
		}
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Double getPopularity() {
		return popularity;
	}

	public void setPopularity(Double popularity) {
		this.popularity = popularity;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public List<Object> getProductionCompanies() {
		return productionCompanies;
	}

	public void setProductionCompanies(List<Object> productionCompanies) {
		this.productionCompanies = productionCompanies;
	}

	public List<ProductionCountry> getProductionCountries() {
		return productionCountries;
	}

	public void setProductionCountries(
			List<ProductionCountry> productionCountries) {
		this.productionCountries = productionCountries;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getRevenue() {
		return revenue;
	}

	public void setRevenue(Integer revenue) {
		this.revenue = revenue;
	}

	public Integer getRuntime() {
		if(this.runtime == null) {
			return 0;
		}
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	public List<SpokenLanguage> getSpokenLanguages() {
		return spokenLanguages;
	}

	public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(Double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}
}
