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

	/**
	 * Sends a HTTP GET request to {@link openHabUrlWithPort}/links to retrieve all
	 * existing links.
	 * 
	 * @return a list of {@link LinksDTO} objects found
	 */
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

	/**
	 * Sends a HTTP GET request to
	 * {@link openHabUrlWithPort}/links/{@link itemName}/{@link channelUid} to get
	 * the specific {@link LinksDTO} identified by the given channelUid and
	 * itemName.
	 * 
	 * @return the {@link LinksDTO} identified by the given channelUid and itemName.
	 */
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

	/**
	 * Sends a HTTP PUT request to
	 * {@link openHabUrlWithPort}/links/{@link itemName}/{@link channelUid} to
	 * create the specific {@link LinksDTO} identified by the given channelUid and
	 * itemName with the body specified in {@link body}.
	 * 
	 * @return true: if successful<br/>
	 *         else false
	 */
	public boolean createLink(String openHabUrlWithPort, String channelUid, String itemName, String body) {
		try {
			return sharedService.sendPutWithPathParameters(openHabUrlWithPort + "/rest/links/" + itemName + "/" + channelUid, body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Sends a HTTP DELETE request to
	 * {@link openHabUrlWithPort}/links/{@link itemName}/{@link channelUid} to
	 * delete the specific {@link LinksDTO} identified by the given channelUid and
	 * itemName.
	 * 
	 * @return true: if successful<br/>
	 *         else false
	 */
	public boolean deleteLink(String openHabUrlWithPort, String channelUid, String itemName) {
		try {
			return sharedService.sendDelete(openHabUrlWithPort + "/rest/links/" + itemName + "/" + channelUid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
