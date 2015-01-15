package com.timkranen.tmdb.domain;

import android.graphics.Bitmap;

public class Media {
	private Bitmap img;
	private String txt;
	public Bitmap getImg() {
		return img;
	}
	public void setImg(Bitmap img) {
		this.img = img;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	
	
}
