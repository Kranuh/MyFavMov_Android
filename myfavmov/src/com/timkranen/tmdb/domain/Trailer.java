package com.timkranen.tmdb.domain;

import java.util.List;

public class Trailer {
	private Encoding encodings;

	public Encoding getEncodings() {
		return encodings;
	}

	public void setEncodings(Encoding encodings) {
		this.encodings = encodings;
	}

	public Format getAvailableFormat() {
		if (encodings.getFire600() != null
				&& !encodings.getFire600().getUrl().isEmpty()) {
			return encodings.getFire600();
		} else if (encodings.getHd480p() != null
				&& !encodings.getHd480p().getUrl().isEmpty()) {
			return encodings.getHd480p();
		} else if (encodings.getHd720p() != null
				&& !encodings.getHd720p().getUrl().isEmpty()) {
			return encodings.getHd720p();
		}

		// this is never returned! or should never be returned, the null check
		// for format is in data.hastrailer()
		return null;

	}

}
