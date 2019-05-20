package me.steffenjacobs.openhabrequester.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/** @author Steffen Jacobs */
public class OpenHabValueRetrieverService {

	/**
	 * @return The current value of the item with the corresponding
	 *         {@link #itemName} on the running openHAB instance specified by
	 *         {@link #openHabUrlWithPort}.
	 */
	public String getValueFromItemByName(String itemName, String openHabUrlWithPort) {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(openHabUrlWithPort + "/rest/items/" + itemName + "/state").openConnection();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				StringBuilder sb = new StringBuilder();
				in.lines().forEach(sb::append);
				return sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
