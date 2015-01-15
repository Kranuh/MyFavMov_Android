package com.timkranen.tools;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoTool {
	public static void doLoad(Context context, ImageView imgView, String path) {
		Picasso.with(context).load(path).into(imgView);
	}
	
	public static Bitmap loadMediaImage(Context context, String path) {
		try {
			Bitmap original = Picasso.with(context).load(path).get();
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(original, 300, 400, false);
			return scaledBitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch(OutOfMemoryError e) {
			return null;
		}
	}
}
