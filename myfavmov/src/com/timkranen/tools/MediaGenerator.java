package com.timkranen.tools;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.myfavmov.R;
import com.timkranen.tmdb.domain.Cast;
import com.timkranen.tmdb.domain.Media;

public abstract class MediaGenerator extends
		AsyncTask<List<Cast>, Void, List<Media>> {

	private Context context;

	public MediaGenerator(Context context) {
		this.context = context;
	}

	@Override
	protected List<Media> doInBackground(List<Cast>... params) {
		List<Cast> fullCast = params[0];
		List<Media> allMedia = new ArrayList<Media>();
		Media m = null;
		for (Cast c : fullCast) {
			// download image
			Bitmap actorImage = null;
			if (c.getActor().getImage() != null) {
				actorImage = PicassoTool.loadMediaImage(context, c.getActor()
						.getImage().getUrl());
			}
			m = new Media();
			m.setImg(actorImage);
			m.setTxt(c.getActor().getName());
			allMedia.add(m);
		}
		return allMedia;
	}

	@Override
	public void onPostExecute(List<Media> result) {
		done(result);
	}

	public abstract void done(List<Media> generatedMedia);

}
