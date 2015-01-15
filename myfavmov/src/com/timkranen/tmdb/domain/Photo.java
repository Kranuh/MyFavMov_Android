package com.timkranen.tmdb.domain;

public class Photo {
	private String link;
	private Image image;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}
