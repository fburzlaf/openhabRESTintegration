package me.steffenjacobs.openhabrequester.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.steffenjacobs.openhabrequester.domain.item.ItemDTO;
import me.steffenjacobs.openhabrequester.domain.item.creation.ItemCreationDTO;

/** @author Steffen Jacobs */
public final class OpenHabItemService {

	private final OpenHabSharedService sharedService = new OpenHabSharedService();

	public List<ItemDTO> requestItems(String openHabUrlWithPort) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/items?recursive=true"), new TypeReference<List<ItemDTO>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public boolean createItem(String openHabUrlWithPort, List<NameValuePair> parameters, ItemCreationDTO item) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String itemJson = "[" + objectMapper.writeValueAsString(item) + "]";
			return sharedService.sendPut(openHabUrlWithPort + "/rest/items", parameters, itemJson);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
