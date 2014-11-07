package com.timkranen.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfavmov.R;
import com.timkranen.tmdb.domain.Media;
import com.timkranen.tools.PicassoTool;

public class MediaListAdapter extends ArrayAdapter<Media> {
	
	private List<Media> objects;
	private Context context;

	public MediaListAdapter(Context context, int resource, List<Media> objects) {
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View rowView = inflater.inflate(R.layout.media_listitem, null);
		
		ImageView img = (ImageView) rowView.findViewById(R.id.media_listitem_img);
		TextView txt = (TextView) rowView.findViewById(R.id.media_listitem_txt);
		
		Media m = objects.get(position);
		
		if(m.getImg() != null) {
			img.setImageBitmap(m.getImg());
		} else {
			img.setImageResource(R.drawable.noactorimg);
		}
		
		if(m.getTxt() != null || !m.getTxt().isEmpty()) {
			txt.setText(m.getTxt());
		}
		
		return rowView;
		
	}

}
