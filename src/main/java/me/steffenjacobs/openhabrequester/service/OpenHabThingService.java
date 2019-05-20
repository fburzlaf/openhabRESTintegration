package me.steffenjacobs.openhabrequester.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.steffenjacobs.openhabrequester.domain.thing.ThingDTO;

public class OpenHabThingService {

	private final OpenHabSharedService sharedService = new OpenHabSharedService();

	public List<ThingDTO> requestThings(String openHabUrlWithPort) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/things"), new TypeReference<List<ThingDTO>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public Optional<ThingDTO> requestThingByName(String openHabUrlWithPort, String thingUID) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
//			objectMapper.setSerializationInclusion(Include.NON_NULL);
			return Optional.of(objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/things/" + thingUID), new TypeReference<ThingDTO>() {
			}));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(null);
	}
	

	public int createThing(String openHabUrlWithPort, /*List<NameValuePair> parameters, */ThingDTO item) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String itemJson = objectMapper.writeValueAsString(item);
			return sharedService.sendPost(openHabUrlWithPort + "/rest/things", itemJson);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int updateThing(String openHabUrlWithPort, List<NameValuePair> parameters, ThingDTO item, String thingUID) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String itemJson = objectMapper.writeValueAsString(item);
			
			return sharedService.sendPutWithPathParameters(openHabUrlWithPort + "/rest/things/" + thingUID, itemJson);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean deleteThing(String openHabUrlwithPort, String thingUID) {
		try {
			return sharedService.sendDelete(openHabUrlwithPort + "/rest/things/" + thingUID + "?force=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateThingState(String openHabUrlwithPort, String thingUID, String body) {
		try {
			return sharedService.sendPut(openHabUrlwithPort + "/rest/things/" + thingUID + "/enable", new ArrayList<NameValuePair>() ,body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String getThingState(String openHabUrlwithPort, String thingUID) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(openHabUrlwithPort + "/rest/things/" + thingUID + "/status");
		

		
		HttpResponse response;
		try {
			response = client.execute(get);
			System.out.println(response.getStatusLine().getStatusCode());
//			System.out.println(url);
			
			BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
			
			String handleResponse = basicResponseHandler.handleResponse(response);
			
			return handleResponse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	
	}
	
}
