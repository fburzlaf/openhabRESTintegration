package me.steffenjacobs.openhabrequester.service.generation;

import me.steffenjacobs.openhabrequester.domain.item.JSONItem;
import me.steffenjacobs.openhabrequester.domain.item.creation.ItemCreationDTO;

/** @author Steffen Jacobs */
public class ItemGenerationService {

	public String generateItemConfig(JSONItem item, String brokerName) {
		StringBuilder sb = new StringBuilder();
		sb.append(generateJson(item, brokerName));
		for (ItemCreationDTO itemc : item.getJsonMappings().values()) {
			sb.append(generateItemConfigWithoutJson(itemc, brokerName));
		}
		return sb.toString();
	}

	public String generateItemConfigWithoutJson(ItemCreationDTO item, String brokerName) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("Number ");
		sb.append(item.getName());
		sb.append(" \"");
		sb.append(item.getLabel());
		sb.append("\"");
		return sb.toString();
	}

	public String generateJson(JSONItem item, String brokerName) {
		StringBuilder sb = new StringBuilder();
		sb.append("String ");
		sb.append(item.getName());
		sb.append(" \"");
		sb.append(item.getName());
		sb.append("\" {mqtt=\"<[");
		sb.append(brokerName);
		sb.append(":/sensor/");
		sb.append(item.getName());
		sb.append(":state:default]\"}");
		return sb.toString();
	}
}
