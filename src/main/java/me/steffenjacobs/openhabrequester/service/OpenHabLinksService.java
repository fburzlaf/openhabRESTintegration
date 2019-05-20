package me.steffenjacobs.openhabrequester.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.steffenjacobs.openhabrequester.domain.links.LinksDTO;
import me.steffenjacobs.openhabrequester.domain.thing.creation.LinksCreationDTO;

public class OpenHabLinksService {

	private final OpenHabSharedService sharedService = new OpenHabSharedService();

	public List<LinksDTO> requestLinks(String openHabUrlWithPort) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/links"), new TypeReference<List<LinksDTO>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public boolean createLink(String openHabUrlWithPort, LinksCreationDTO item, List<NameValuePair> parameters) {
		ObjectMapper objectMapper = new ObjectMapper();
		parameters.add(new BasicNameValuePair("itemName", item.getItemName()));
		parameters.add(new BasicNameValuePair("channeUID", item.getChanneUID()));
		try {
			String itemJson = "[" + objectMapper.writeValueAsString(item.getBody()) + "]";
			return sharedService.sendPut(openHabUrlWithPort + "/rest/links/", parameters, itemJson);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
