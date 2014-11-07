package com.timkranen.fragments;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfavmov.ProfileActivity;
import com.example.myfavmov.R;
import com.timkranen.adapters.MovieListAdapter;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.tools.LocalStorageTool;

public class WatchlistFragment extends Fragment {

	private View contentView;
	List<Movie> watchlist;

	// ui
	private ListView watchlistListView;
	private TextView noMoviesTxt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.watchlist_fragment, container,
				false);
		initComponents();
		setWatchList();
		watchlistListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				((ProfileActivity) getActivity())
						.navigateToMovieActivity(watchlist.get(arg2).getId());
			}
		});

		return contentView;
	}
	

	private void setWatchList() {
		Context appContext = getActivity().getApplicationContext();
		LocalStorageTool sTool = new LocalStorageTool(appContext);
		watchlist = sTool.getWatchlist();
		if (watchlist != null && watchlist.size() > 0) {
			MovieListAdapter mListAdapter = new MovieListAdapter(getActivity(),
					R.layout.movcol_listitem, watchlist);
			watchlistListView.setAdapter(mListAdapter);
		} else {
			noMoviesTxt.setVisibility(View.VISIBLE);
		}
	}

	private void initComponents() {
		watchlistListView = (ListView) contentView
				.findViewById(R.id.watchlistfragment_listview);
		noMoviesTxt = (TextView) contentView
				.findViewById(R.id.watchlistfragment_nomoviestxt);
	}

}
