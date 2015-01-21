package com.timkranen.tmdb;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.widget.ListView.FixedViewInfo;

public class HttpService {
	
	public static String getJson(String url) throws IOException {
		url = fixSpaces(url);
		HttpGet post = new HttpGet(url);
		post.addHeader("accept", "application/json");
		post.addHeader("Content-length", "0");
		AndroidHttpClient client = AndroidHttpClient.newInstance("DATA");
		
		//response
		HttpResponse response = client.execute(post);
		
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity);
		client.close();
		return result;
	}
	
	private static String fixSpaces(String url) {
		//can't do it with encoding because that ruins the host at this point
//		for(int i = 0; i < url.length(); i++) {
//			if(url.charAt(i) == ' ') {
				url = url.replace(" ", "%20");
//			}
//		}
		
		return url;
	}

}
