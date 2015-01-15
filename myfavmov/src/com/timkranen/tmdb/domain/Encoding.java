package com.timkranen.tmdb.domain;

import com.google.gson.annotations.SerializedName;


public class Encoding {
	
	@SerializedName("H.264 Fire 600")
	private Format fire600;
	@SerializedName("HD 480p")
	private Format hd480p;
	@SerializedName("HD 720p")
	private Format hd720p;
	@SerializedName("iPhone 3g")
	private Format iPhoneFormat;
	public Format getFire600() {
		return fire600;
	}
	public void setFire600(Format fire600) {
		this.fire600 = fire600;
	}
	public Format getHd480p() {
		return hd480p;
	}
	public void setHd480p(Format hd480p) {
		this.hd480p = hd480p;
	}
	public Format getHd720p() {
		return hd720p;
	}
	public void setHd720p(Format hd720p) {
		this.hd720p = hd720p;
	}
	public Format getiPhoneFormat() {
		return iPhoneFormat;
	}
	public void setiPhoneFormat(Format iPhoneFormat) {
		this.iPhoneFormat = iPhoneFormat;
	}
	
	
	
}
