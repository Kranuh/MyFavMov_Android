package com.timkranen.tmdb.domain;

import com.google.gson.annotations.SerializedName;

public class Cast {
	@SerializedName("char")
	private String characterName;
	@SerializedName("name")
	private Actor actor;
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
}
