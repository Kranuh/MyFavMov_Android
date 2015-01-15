package com.timkranen.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.myfavmov.MainActivity;
import com.example.myfavmov.R;
import com.timkranen.parallaxview.ParallaxImageView;
import com.timkranen.tmdb.SearchTask;
import com.timkranen.tmdb.domain.Movie;
import com.timkranen.verticalviewpager.VerticalViewPager;

public class SearchFragment extends Fragment {

	private View contentView;
	private MainActivity mainActivity;

	private GestureDetector gDetector;

	private ParallaxImageView mBackground;

	// main ui
	private TextView slideToStartTxt;
	private VerticalViewPager viewPager;
	private EditText searchEditText;
	private ImageView favmovLogo;
	private Button profileButton;

	Animation slide_in_left, slide_out_right;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.search_fragment, container,
				false);
		mainActivity = (MainActivity) getActivity();
		initComponents();
		setupParallax();
		startTextAnimation();
		searchEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String query = searchEditText.getText().toString();
					if (query != null && !query.isEmpty()) {
						InputMethodManager imm = (InputMethodManager) getActivity()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(
								searchEditText.getWindowToken(), 0);
						startSearch(query);
					} else {
						return false;
					}

					return true;
				}

				return false;
			}
		});

		return contentView;
	}

	private void startSearch(String query) {
		startLoadingAnimation();
		SearchTask sTask = new SearchTask(query) {

			@Override
			public void ioExceptionRaised() {
				((MainActivity) getActivity())
						.navigateToMovCollectionActivity(new ArrayList<Movie>());
			}

			@Override
			public void done(List<Movie> result) {
				//favmovLogo.clearAnimation();
				((MainActivity) getActivity())
						.navigateToMovCollectionActivity(result);
			}
		};

		Thread searchThread = new Thread(sTask);
		searchThread.start();

	}

	private void startLoadingAnimation() {
		Animation anim = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anim.setDuration(1000);
		anim.setRepeatCount(Animation.INFINITE);
		favmovLogo.startAnimation(anim);
	}

	private void initComponents() {
		mBackground = (ParallaxImageView) contentView
				.findViewById(R.id.pBackground);
		slideToStartTxt = (TextView) contentView
				.findViewById(R.id.search_txt_slide);
		viewPager = (VerticalViewPager) contentView
				.findViewById(R.id.search_viewPager);
		viewPager.setAdapter(new ViewPagerAdapter());
		searchEditText = (EditText) contentView
				.findViewById(R.id.search_etxt_query);
		favmovLogo = (ImageView) contentView.findViewById(R.id.logo);
		profileButton = (Button) contentView
				.findViewById(R.id.search_bt_profile);
		
		profileButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).navigateToProfileActivity();
			}
		});
	}

	private void startTextAnimation() {
		Animation anim = new AlphaAnimation(0.2f, 1.0f);
		anim.setDuration(1000); // You can manage the time of the blink with //
								// this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		slideToStartTxt.startAnimation(anim);
	}

	private void setupParallax() {
		mBackground.setImageResource(R.drawable.par_background2);
		mBackground.registerSensorManager();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(View collection, int position) {
			int resId = 0;
			switch (position) {
			case 0:
				resId = R.id.startLayout;
				break;
			case 1:
				resId = R.id.searchLayout_main;
				break;
			}
			return contentView.findViewById(resId);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == ((View) arg1);
		}
	}

}
