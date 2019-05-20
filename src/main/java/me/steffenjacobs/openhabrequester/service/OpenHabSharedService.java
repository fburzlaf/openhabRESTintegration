package me.steffenjacobs.openhabrequester.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.StyleConstants.ColorConstants;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/** @author Steffen Jacobs */
public final class OpenHabSharedService {

	OpenHabSharedService() {
	}

	public boolean sendPut(String url, List<NameValuePair> parameters, String payload) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(url);
		put.setEntity(new UrlEncodedFormEntity(parameters, Consts.UTF_8));
		put.addHeader("Content-Type", "application/json");
		
		HttpResponse response = client.execute(put);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(url);
		return 200 == response.getStatusLine().getStatusCode();
	}
	
	public boolean sendPost(String url, String payload) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		post.setEntity(new ByteArrayEntity(payload.getBytes("UTF-8")));
		post.addHeader("Content-Type", "application/json");
		HttpResponse response = client.execute(post);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(url);
		return 200 == response.getStatusLine().getStatusCode();
	}
	
	/**
	 *  TO BE IMPLEMENTED
	 * @param URL
	 */
	public void listenToServerEvents(String URL) {
	}
}
