package me.steffenjacobs.openhabrequester.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.steffenjacobs.openhabrequester.domain.links.LinksDTO;

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

	public LinksDTO requestLinksByChannelUidAndItemName(String openHabUrlWithPort, String channelUID, String itemName) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/links" + "/" + itemName + "/" + channelUID), new TypeReference<LinksDTO>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean createLink(String openHabUrlWithPort, String channelUid, String itemName, String body) {
		try {
			return sharedService.sendPutWithPathParameters(openHabUrlWithPort + "/rest/links/" + itemName + "/" + channelUid, body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteLink(String openHabUrlWithPort, String channelUid, String itemName) {
		try {
			return sharedService.sendDelete(openHabUrlWithPort + "/rest/links/" + itemName + "/" + channelUid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
