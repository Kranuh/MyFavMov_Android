package com.timkranen.fragments;

import java.io.IOException;
import java.util.List;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.example.myfavmov.MovieActivity;
import com.example.myfavmov.R;
import com.meetme.android.horizontallistview.HorizontalListView;
import com.squareup.picasso.Picasso;
import com.timkranen.adapters.MediaListAdapter;
import com.timkranen.parallaxview.ParallaxImageView;
import com.timkranen.tmdb.MovService;
import com.timkranen.tmdb.domain.Cast;
import com.timkranen.tmdb.domain.ImdbMovie;
import com.timkranen.tmdb.domain.Media;
import com.timkranen.tmdb.domain.TmdbMovie;
import com.timkranen.tools.BlurTool;
import com.timkranen.tools.LocalStorageTool;
import com.timkranen.tools.MediaGenerator;
import com.timkranen.tools.PicassoTool;

public class MovieFragment extends Fragment {

	private View contentView;
	private int movieId;
	private TmdbMovie selectedTmdbMovie;
	private ImdbMovie selectedImdbMovie;
	private boolean isWatchingTrailer;

	// ui
	private ParallaxImageView backdrop;
	private TextView movieTitle;
	private ImageView poster;
	private TextView releaseDate;
	private TextView runtime;
	private TextView genres;
	private TextView rating;
	private TextView synopsis;
	private HorizontalListView castListView;
	private ProgressBar castProgressBar;
	private RelativeLayout backdropLayout;
	private VideoView trailerView;
	private RelativeLayout trailerLayout;
	private RelativeLayout trailerCardLayout;
	private ViewSwitcher headerSwitcher;
	private TextView trailerText;
	private TextView noCastTxt;

	private Handler mHandler = new Handler();

	public static MovieFragment newInstance(int movieId) {
		MovieFragment mFragment = new MovieFragment();
		Bundle args = new Bundle();
		args.putInt("movie_id", movieId);
		mFragment.setArguments(args);
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.movie_fragment, container,
				false);
		((MovieActivity) getActivity()).setActionBarTitle("");
		setMovie();
		setHasOptionsMenu(true);
		initComponents();
		MovieFetcher mFetcher = new MovieFetcher();
		mFetcher.execute();
		return contentView;
	}

	private void setMovie() {
		this.movieId = getArguments().getInt("movie_id");
	}

	private void initComponents() {
		backdrop = (ParallaxImageView) contentView
				.findViewById(R.id.movfragment_backdrop_img);
		movieTitle = (TextView) contentView
				.findViewById(R.id.movfragment_movtitle_txt);
		poster = (ImageView) contentView
				.findViewById(R.id.movfragment_poster_img);
		castListView = (HorizontalListView) contentView
				.findViewById(R.id.movfragment_cast_castlist);
		releaseDate = (TextView) contentView
				.findViewById(R.id.movfragment_geninfo_label_release);
		runtime = (TextView) contentView
				.findViewById(R.id.movfragment_geninfo_label_runtime);
		genres = (TextView) contentView
				.findViewById(R.id.movfragment_geninfo_label_genre);
		rating = (TextView) contentView
				.findViewById(R.id.movfragment_geninfo_rating);
		synopsis = (TextView) contentView
				.findViewById(R.id.movfragment_synopsis_txt);
		castProgressBar = (ProgressBar) contentView
				.findViewById(R.id.movfragment_cast_progressbar);
		backdropLayout = (RelativeLayout) contentView
				.findViewById(R.id.movfragment_backdrop_layout);
		trailerView = (VideoView) contentView
				.findViewById(R.id.movfragment_trailer_videoview);
		trailerLayout = (RelativeLayout) contentView
				.findViewById(R.id.movfragment_trailer_wrapper);
		trailerCardLayout = (RelativeLayout) contentView
				.findViewById(R.id.movfragment_trailer_card);
		headerSwitcher = (ViewSwitcher) contentView
				.findViewById(R.id.movfragment_header_viewswitcher);
		trailerText = (TextView) contentView
				.findViewById(R.id.movfragment_trailer_txt);
		noCastTxt = (TextView) contentView
				.findViewById(R.id.movfragment_cast_nocast_txt);
	}

	private void movieDoneLoading() {
		((MovieActivity) getActivity()).setActionBarTitle(selectedTmdbMovie
				.getTitle());
		((MovieActivity) getActivity()).setLocalMovie(
				selectedTmdbMovie.getTitle(),
				selectedTmdbMovie.getReleaseDate(),
				selectedTmdbMovie.getBackdropPath());
		((MovieActivity) getActivity()).invalidateOptions();
		startImdbMovieLoad(selectedTmdbMovie.getImdbId());
		BackdropHandler bHandler = new BackdropHandler();
		bHandler.execute();

		// initiate the movie data that was retrieved from tmdb
		movieTitle.setText(selectedTmdbMovie.getTitle() + " ("
				+ getMovieYear(selectedTmdbMovie) + ")");
		if (selectedTmdbMovie.getPosterPath() != null) {
			PicassoTool.doLoad(getActivity(), poster, MovService
					.getPosterBackdrop(selectedTmdbMovie.getPosterPath()));
		}

		releaseDate.setText("Release date: "
				+ selectedTmdbMovie.getReleaseDate());
		if (selectedTmdbMovie.getRuntime() > 0) {
			runtime.setText("Runtime: "
					+ selectedTmdbMovie.getRuntime().toString() + " minutes");
		} else {
			runtime.setText("Runtime: N/A");
		}

		synopsis.setText(selectedTmdbMovie.getOverview());
	}

	private void startImdbMovieLoad(String imdbId) {
		String[] params = new String[] { imdbId };
		ImdbMovieFetcher mFetcher = new ImdbMovieFetcher();
		mFetcher.execute(params);
	}

	private void imdbMovieLoaded() {
		if (selectedImdbMovie != null && selectedImdbMovie.getData() != null) {
			((MovieActivity) getActivity()).setLocalImdbMovie(selectedImdbMovie);
			rating.setText(selectedImdbMovie.getData().getRating() + "/10");
			genres.setText("Genres: " + selectedImdbMovie.getData().getGenres());
			generateMediaFromCast(selectedImdbMovie.getData().getCast_summary());

			// check if the movie has a trailer
			if (selectedImdbMovie.getData().hasTrailer()) {
				trailerCardLayout.setVisibility(View.VISIBLE);
				trailerCardLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (isWatchingTrailer) {
							trailerView.pause();
							trailerText.setText("Watch Trailer");
							headerSwitcher.showNext();
							isWatchingTrailer = false;
						} else {
							headerSwitcher.showNext();
							trailerText.setText("Stop Trailer");
							trailerView.setVideoURI(Uri.parse(selectedImdbMovie
									.getData().getTrailer()
									.getAvailableFormat().getUrl()));
							trailerView.start();
							isWatchingTrailer = true;
						}
					}
				});
			}
		} else {
			rating.setText("N/A");
			genres.setText("Genre(s): N/A");
		}
	}

	private void setMediaAdapter(List<Media> media) {
		MediaListAdapter mListAdapter = new MediaListAdapter(getActivity(),
				R.layout.media_listitem, media);
		castProgressBar.setVisibility(View.GONE);
		castListView.setVisibility(View.VISIBLE);
		castListView.setAdapter(mListAdapter);
	}

	private void generateMediaFromCast(List<Cast> cast) {
		if (cast != null && cast.size() > 0) {
			MediaGenerator mGenerator = new MediaGenerator(getActivity()) {

				@Override
				public void done(List<Media> generatedMedia) {
					setMediaAdapter(generatedMedia);
				}
			};

			mGenerator.execute(cast);
		} else {
			castProgressBar.setVisibility(View.GONE);
			noCastTxt.setVisibility(View.VISIBLE);
		}
	}

	private void setBlurredBitmap(Bitmap bMap) {
		if (bMap != null) {
			backdrop.setImageBitmap(bMap);
			backdrop.registerSensorManager();
		}
	}

	private String getMovieYear(TmdbMovie mov) {
		try {
			return mov.getReleaseDate().substring(0, 4);
		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}

	}

	private class ImdbMovieFetcher extends AsyncTask<String, Void, ImdbMovie> {

		@Override
		protected ImdbMovie doInBackground(String... params) {
			String imdbId = params[0];
			ImdbMovie m = null;
			MovService mService = new MovService();
			try {
				m = mService.getImdbMovie(imdbId);
			} catch (IOException e) {
			}
			return m;
		}

		@Override
		public void onPostExecute(ImdbMovie result) {
			if (result != null) {
				selectedImdbMovie = result;
				imdbMovieLoaded();
			} else {
				// error msg
			}
		}
	}

	private class MovieFetcher extends AsyncTask<Void, Void, TmdbMovie> {

		@Override
		protected TmdbMovie doInBackground(Void... arg0) {
			TmdbMovie m = null;
			try {
				MovService mService = new MovService();
				m = mService.getMovie(MovieFragment.this.movieId);
				return m;
			} catch (IOException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(TmdbMovie result) {
			if (result != null) {
				selectedTmdbMovie = result;
				movieDoneLoading();
			} else {
				// error msg
			}
		}
	}

	private class BackdropHandler extends AsyncTask<Void, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				Bitmap backdrop = Picasso
						.with(getActivity())
						.load(MovService.getMovieBackdrop(selectedTmdbMovie
								.getBackdropPath())).get();
				if (backdrop != null) {
					return BlurTool.fastblur(backdrop, 5);
				} else {
					return BlurTool.fastblur(BitmapFactory.decodeResource(
							getResources(), R.drawable.nobackdrop), 5);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			setBlurredBitmap(result);
		}

	}
}
