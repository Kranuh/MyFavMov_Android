package com.timkranen.tmdb.domain;

public class Actor {
	private String name;
	private Image image;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public boolean hasImage() {
		if(image != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
