package com.timkranen.tools;

import java.util.Comparator;
import java.util.Map;

public class GenreComparator implements Comparator<String>{
	
	Map<String, Integer> map;

	public GenreComparator(Map<String, Integer> base) {
		this.map = base;
	}
	
	@Override
	public int compare(String a, String b) {
		if(map.get(a) > map.get(b)) {
			return 1;
		} else {
			return -1;
		}
	}
	
	

}
