package me.steffenjacobs.openhabrequester.domain.item;

import java.util.Map;

import me.steffenjacobs.openhabrequester.domain.item.creation.ItemCreationDTO;

/** @author Steffen Jacobs */
public class JSONItem {
	private String name;
	private Map<String, ItemCreationDTO> jsonMappings;

	public JSONItem(String name, Map<String, ItemCreationDTO> jsonMappings) {
		this.name = name;
		this.jsonMappings = jsonMappings;
	}

	public String getName() {
		return name;
	}

	public Map<String, ItemCreationDTO> getJsonMappings() {
		return jsonMappings;
	}

}
