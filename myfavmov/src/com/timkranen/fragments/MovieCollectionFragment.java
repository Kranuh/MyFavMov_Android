package com.timkranen.fragments;

import java.util.Collections;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfavmov.MovieCollectionActivity;
import com.example.myfavmov.R;
import com.timkranen.adapters.MovieListAdapter;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tmdb.serializer.GsonSerializer;

public class MovieCollectionFragment extends Fragment {

	private View contentView;

	private List<Movie> movies;

	private ListView movListView;
	private View movcolActionBar;
	private TextView movcolErrorTxt;
	private ImageView movcolErrorImg;

	public static MovieCollectionFragment newInstance(List<Movie> movies) {
		MovieCollectionFragment newFragment = new MovieCollectionFragment();
		Bundle args = new Bundle();
		args.putString("movie_list", GsonSerializer.fromMovieList(movies));
		newFragment.setArguments(args);
		return newFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.movcol_fragment, container,
				false);
		initComponents();
		setupMovies();
		return contentView;
	}

	private void initComponents() {
		movListView = (ListView) contentView.findViewById(R.id.movcol_list);
		movcolActionBar = (View) contentView
				.findViewById(R.id.movcol_actionbar);
		movcolErrorTxt = (TextView) contentView
				.findViewById(R.id.movcol_noresults_txt);
		movcolErrorImg = (ImageView) contentView
				.findViewById(R.id.movcol_noresults_img);
	}

	private void setupAdapter() {
		MovieListAdapter adapter = new MovieListAdapter(getActivity(),
				R.layout.movcol_listitem, movies);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(R.layout.movcol_header,
				movListView, false);
		movListView.addHeaderView(header);
		movListView.setAdapter(adapter);
		movListView.setDividerHeight(0);
		movListView.setDivider(null);
		movListView.setOnItemClickListener(new MovieClickListener());
	}

	private void setupMovies() {
		Bundle args = getArguments();
		String json = args.getString("movie_list");
		this.movies = GsonSerializer.movieListToJson(json);
		if (this.movies.size() > 0) {
			Collections.sort(movies);
			setupAdapter();
		} else {
			movcolActionBar.setVisibility(View.VISIBLE);
			movcolErrorTxt.setVisibility(View.VISIBLE);
			movcolErrorImg.setVisibility(View.VISIBLE);
		}
	}
	
	private class MovieClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Movie selectedMovie = movies.get(position - 1);
			((MovieCollectionActivity) getActivity()).navigateToMovie(selectedMovie.getId());
		}
		
	}

}
