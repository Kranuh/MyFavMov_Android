package com.timkranen.fragments;

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

import com.example.myfavmov.R;
import com.timkranen.parallaxview.ParallaxImageView;

public class ProfileFragment extends Fragment {

	private View contentView;
	private boolean animIsShown = false;

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
		
		ParallaxImageView bg = (ParallaxImageView) contentView.findViewById(R.id.statfragment_bg);
		bg.setImageResource(R.drawable.drivein);
		bg.registerSensorManager();
		return contentView;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (!animIsShown) {
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
	}

	private void startAnimations() {
		startBarAnimations();
		animIsShown = true;
	}

	private void startBarAnimations() {
		// first animation
		firstGenreBar.setVisibility(View.VISIBLE);
		firstGenreBar.startAnimation(getSlideIn(-.05f));
		secondGenreBar.setVisibility(View.VISIBLE);
		secondGenreBar.startAnimation(getSlideIn(-.3f));
		thirdGenreBar.setVisibility(View.VISIBLE);
		thirdGenreBar.startAnimation(getSlideIn(-.7f));
		firstGenreTextLayout.bringToFront();
		secondGenreTextLayout.bringToFront();
		thirdGenreTextLayout.bringToFront();

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

	private Point getScreenSize() {
		Point size = new Point();
		int Measuredwidth;
		int Measuredheight;
		WindowManager w = getActivity().getWindowManager();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			w.getDefaultDisplay().getSize(size);
		} else {
			Display d = w.getDefaultDisplay();
			Measuredwidth = d.getWidth();
			Measuredheight = d.getHeight();
			size.x = Measuredwidth;
			size.y = Measuredheight;
		}
		return size;
	}

}
