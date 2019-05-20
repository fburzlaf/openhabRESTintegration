package me.steffenjacobs.openhabrequester.service;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import me.steffenjacobs.openhabrequester.domain.GenericNode;
import me.steffenjacobs.openhabrequester.domain.item.ItemDTO;
import me.steffenjacobs.openhabrequester.domain.item.StateDescriptionDTO;

/** @author Steffen Jacobs */
public final class ItemTransformerService {

	public ItemTransformerService() {
	}

	public enum MetaState {
		ItemNode, ItemNodeList, StateDescription, Collection, Map, Entry;
	}

	/**
	 * transforms a {@link Iterable} of {@link ItemDTO} into a
	 * {@link GenericNode}
	 */
	public GenericNode<MetaState> transformToTree(Iterable<ItemDTO> items) {
		GenericNode<MetaState> result = new GenericNode<>(MetaState.ItemNodeList, "items");
		items.forEach(t -> result.appendChild(transformToTree(t)));
		return result;
	}

	/** transforms a {@link ItemDTO} into a {@link GenericNode} */
	private GenericNode<MetaState> transformToTree(ItemDTO item) {
		GenericNode<MetaState> itemNode = new GenericNode<>(MetaState.ItemNode, "item");
		GenericNode<Boolean> editable = new GenericNode<>(item.getEditable(), "editable");
		GenericNode<String> label = new GenericNode<>(item.getLabel(), "label");
		GenericNode<String> link = new GenericNode<>(item.getLink(), "link");
		GenericNode<String> name = new GenericNode<>(item.getName(), "name");
		GenericNode<String> state = new GenericNode<>(item.getState(), "state");
		GenericNode<String> type = new GenericNode<>(item.getType(), "type");

		GenericNode<MetaState> objects = transformToTree(item.getGroupNames(), "groupNames", "groupName");
		GenericNode<MetaState> tags = transformToTree(item.getTags(), "tags", "tag");

		GenericNode<MetaState> additionalProperties = transformToTree(item.getAdditionalProperties(), "additionalProerties", "additionalProperty");

		GenericNode<MetaState> stateDescription = transformToTree(item.getStateDescription());

		itemNode.appendChild(editable);
		itemNode.appendChild(label);
		itemNode.appendChild(link);
		itemNode.appendChild(name);
		itemNode.appendChild(state);
		itemNode.appendChild(type);
		itemNode.appendChild(objects);
		itemNode.appendChild(tags);
		itemNode.appendChild(additionalProperties);
		itemNode.appendChild(stateDescription);

		return itemNode;
	}

	/** transforms a {@link StateDescriptionDTO} into a {@link GenericNode} */
	private GenericNode<MetaState> transformToTree(StateDescriptionDTO stateDescription) {
		GenericNode<MetaState> result = new GenericNode<>(MetaState.StateDescription, "stateDescription");

		if (stateDescription == null) {
			return result;
		}

		GenericNode<String> pattern = new GenericNode<>(stateDescription.getPattern(), "pattern");
		GenericNode<Boolean> readOnly = new GenericNode<>(stateDescription.getReadOnly(), "readOnly");

		GenericNode<MetaState> additionalPropertiesState = transformToTree(stateDescription.getAdditionalProperties(), "additionalProperties", "additionalProperty");

		GenericNode<MetaState> options = transformToTree(stateDescription.getOptions(), "options", "option");

		result.appendChild(pattern);
		result.appendChild(readOnly);
		result.appendChild(additionalPropertiesState);
		result.appendChild(options);

		return result;
	}

	/** transforms a {@link Map.Entry} into a {@link GenericNode} */
	private GenericNode<MetaState> transformToTree(Entry<String, Object> entr, String itemName) {
		GenericNode<MetaState> result = new GenericNode<>(MetaState.Entry, itemName);
		GenericNode<?> key = new GenericNode<>(entr.getKey(), "key");
		GenericNode<?> value = new GenericNode<>(entr.getValue(), "value");
		result.appendChild(key);
		result.appendChild(value);
		return result;
	}

	/** transforms a {@link Map} into a {@link GenericNode} */
	private GenericNode<MetaState> transformToTree(Map<String, Object> map, String name, String itemName) {
		GenericNode<MetaState> mapNode = new GenericNode<>(MetaState.Map, name);
		for (Entry<String, Object> property : map.entrySet()) {
			GenericNode<MetaState> pair = transformToTree(property, itemName);
			mapNode.appendChild(pair);
		}
		return mapNode;
	}

	/** transforms a {@link Collection} into a {@link GenericNode} */
	private GenericNode<MetaState> transformToTree(Collection<Object> collection, String name, String itemName) {
		GenericNode<MetaState> collectionNode = new GenericNode<>(MetaState.Collection, name);
		for (Object obj : collection) {
			GenericNode<Object> o = new GenericNode<>(obj, itemName);
			collectionNode.appendChild(o);
		}
		return collectionNode;
	}

}
