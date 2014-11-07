package com.timkranen.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfavmov.R;
import com.squareup.picasso.Picasso;
import com.timkranen.tmdb.MovService;
import com.timkranen.tmdb.domain.Movie;

public class MovieListAdapter extends ArrayAdapter<Movie> {

	private Context context;

	private ImageView backdrop;
	private TextView txt;

	private List<Movie> movies;

	public MovieListAdapter(Context context, int resource, List<Movie> objects) {
		super(context, resource, objects);
		this.context = context;
		this.movies = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View rowView = inflater.inflate(R.layout.movcol_listitem, null);

		backdrop = (ImageView) rowView.findViewById(R.id.movcol_item_backdrop);
		txt = (TextView) rowView.findViewById(R.id.movcol_item_txt);
		if (movies.get(position).getBackdropPath() != null) {
			String url = MovService.getMovieBackdrop(movies.get(position)
					.getBackdropPath());
			Picasso.with(context).load(url).resize(300, 200).into(backdrop);
		} else {

		}

		if (movies.get(position).getReleaseDate() != null
				|| !movies.get(position).getReleaseDate().isEmpty()) {
			try {
				txt.setText(movies.get(position).getTitle() + " ("
						+ movies.get(position).getReleaseDate().substring(0, 4)
						+ ")");
			} catch (IndexOutOfBoundsException e) {
				txt.setText(movies.get(position).getOriginalTitle());
			}
		} else {
			txt.setText(movies.get(position).getOriginalTitle());
		}

		return rowView;
	}

	@Override
	public int getCount() {
		return movies.size();
	}

}
