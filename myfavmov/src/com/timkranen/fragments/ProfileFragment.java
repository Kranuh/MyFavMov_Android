package com.timkranen.fragments;

import java.util.List;
import java.util.Map;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myfavmov.R;
import com.timkranen.parallaxview.ParallaxImageView;
import com.timkranen.tmdb.domain.Statistic;
import com.timkranen.tools.LocalStorageTool;

public class ProfileFragment extends Fragment {

	private View contentView;
	private boolean animIsShown = false;
	private boolean hasStats;
	private Statistic currentStats;

	// no stats ui components
	private ImageView noStatsImg;
	private TextView noStatsTxt;

	// ui components
	private LinearLayout topLayout;
	private TextView ratingTxt, yearTxt, ratingLabelTxt, yearLabelTxt,
			genreLabelTxt;
	private TextView genre1Txt, genre2Txt, genre3Txt;

	// animation ui components
	private View firstGenreBar;
	private View secondGenreBar;
	private View thirdGenreBar;
	private FrameLayout firstGenreTextLayout;
	private FrameLayout secondGenreTextLayout;
	private FrameLayout thirdGenreTextLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.profile_fragment, container,
				false);
		initComponents();
		handleStatistics();
		ParallaxImageView bg = (ParallaxImageView) contentView
				.findViewById(R.id.statfragment_bg);
		bg.setImageResource(R.drawable.drivein);
		bg.registerSensorManager();
		return contentView;
	}

	@Override
	public void onResume() {
		super.onResume();
		handleStatistics();
	}

	private void handleStatistics() {
		loadStatistics();
		hasStats = currentStats.isFilled();
		viewHider(hasStats);
		updateStats();
	}

	private void loadStatistics() {
		LocalStorageTool lsTool = new LocalStorageTool(getActivity()
				.getApplicationContext());
		currentStats = lsTool.getStatistics();
	}

	private void updateStats() {
		ratingTxt.setText(currentStats.getAvgRating() + "/10");
		yearTxt.setText(String.valueOf(currentStats.getAvgReleaseDate()));
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (!animIsShown && hasStats) {
				startAnimations();
			}
		}
	}

	private void initComponents() {
		firstGenreBar = (View) contentView
				.findViewById(R.id.statfragment_genrebar1);
		secondGenreBar = (View) contentView
				.findViewById(R.id.statfragment_genrebar2);
		thirdGenreBar = (View) contentView
				.findViewById(R.id.statfragment_genrebar3);
		firstGenreTextLayout = (FrameLayout) contentView
				.findViewById(R.id.statfragment_firstgenre_text_layout);
		secondGenreTextLayout = (FrameLayout) contentView
				.findViewById(R.id.statfragment_secondgenre_text_layout);
		thirdGenreTextLayout = (FrameLayout) contentView
				.findViewById(R.id.statfragment_thirdgenre_text_layout);

		// no stats ui comps
		noStatsImg = (ImageView) contentView
				.findViewById(R.id.statfragment_nostats);
		noStatsTxt = (TextView) contentView
				.findViewById(R.id.statfragment_nostats_label);

		// ui comps
		topLayout = (LinearLayout) contentView
				.findViewById(R.id.statfragment_top_layout);
		ratingTxt = (TextView) contentView
				.findViewById(R.id.statfragment_rating);
		yearTxt = (TextView) contentView.findViewById(R.id.statfragment_year);
		genre1Txt = (TextView) contentView
				.findViewById(R.id.statfragment_firstgenre);
		genre2Txt = (TextView) contentView
				.findViewById(R.id.statfragment_secondgenre);
		genre3Txt = (TextView) contentView
				.findViewById(R.id.statfragment_thirdgenre);
	}

	private void viewHider(boolean visible) {
		if (visible) {
			// show
			for (int i = 0; i < topLayout.getChildCount(); i++) {
				View child = topLayout.getChildAt(i);
				child.setVisibility(View.VISIBLE);
			}
			noStatsImg.setVisibility(View.INVISIBLE);
			noStatsTxt.setVisibility(View.INVISIBLE);
		} else {
			// hide everything
			// hide toplayout & children
			for (int i = 0; i < topLayout.getChildCount(); i++) {
				View child = topLayout.getChildAt(i);
				child.setVisibility(View.GONE);
			}
			// hide bar text (we don't need to hide the bars, just don't start
			// the
			// animations)
			// TODO
			// show nostats props
			noStatsImg.setVisibility(View.VISIBLE);
			noStatsTxt.setVisibility(View.VISIBLE);
		}

	}

	private void startAnimations() {
		startBarAnimations();
		animIsShown = true;
	}

	private void startBarAnimations() {
		// first animation
		float[] posvalues = calculateBarPositions();
		firstGenreBar.setVisibility(View.VISIBLE);
		firstGenreBar.startAnimation(getSlideIn(posvalues[0]));

		if (posvalues[2] != 0) { // if it's 0 then there aren't 3 top genres yet
			secondGenreBar.setVisibility(View.VISIBLE);
			secondGenreBar.startAnimation(getSlideIn(posvalues[2])); // there's
																		// a
																		// mixup
																		// in
																		// the
																		// layout
																		// secondGenreBar
																		// is
																		// the
																		// bottom
																		// one
		}

		if (posvalues[1] != 0) {
			thirdGenreBar.setVisibility(View.VISIBLE);
			thirdGenreBar.startAnimation(getSlideIn(posvalues[1]));
		}

		firstGenreTextLayout.bringToFront();
		secondGenreTextLayout.bringToFront();
		thirdGenreTextLayout.bringToFront();
	}

	/*
	 * returns an int array based on statistics array[0] = firstmovie in map,
	 * value of bar array[1] = secondmovie in map, value of bar etc... note:
	 * first bar value will always be -.5f or no movies have been watched
	 */
	private float[] calculateBarPositions() {
		float[] posvalues = { 0, 0, 0 };
		List<Map.Entry<String, Integer>> favs = currentStats.getFavGenres();
		// set maxval
		posvalues[0] = -.05f;
		// the values are the difference between max and count
		int i = 0;
		int max = 0;
		for (Map.Entry<String, Integer> entry : favs) {
			if (i != 0) {
				if (entry.getValue() != max) {
					int percentage = (entry.getValue() * 100) / max;
					float fin = (float) (percentage / 100.0);
					posvalues[i] = -fin;
				} else {
					posvalues[i] = -.05f;
				}
				i++;
			} else {
				max = entry.getValue();
				i++;
			}
		}
		setGenreLabels(favs);
		return posvalues;
	}

	private void setGenreLabels(List<Map.Entry<String, Integer>> favs) {
		for (int i = 0; i < 3; i++) {
			switch (i) {
			case 0:
				genre1Txt.setText(favs.get(i).getKey() + " ("
						+ favs.get(i).getValue() + ")");
				break;
			case 1:
				genre2Txt.setText(favs.get(i).getKey() + " ("
						+ favs.get(i).getValue() + ")");
				break;
			case 2:
				genre3Txt.setText(favs.get(i).getKey() + " ("
						+ favs.get(i).getValue() + ")");
				break;
			}
		}
	}

	/*
	 * make x negative! we want them to fligh in from the right side x =
	 * (negative) 100% - whitespace%
	 */
	private Animation getSlideIn(float x) {
		TranslateAnimation anim = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, -1.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, x,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f);
		anim.setDuration(1300);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.setFillAfter(true);
		return anim;
	}

}
