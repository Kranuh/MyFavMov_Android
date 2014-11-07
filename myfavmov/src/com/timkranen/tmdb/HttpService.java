package com.timkranen.tmdb;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;

public class HttpService {
	
	public static String getJson(String url) throws IOException {
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

}
