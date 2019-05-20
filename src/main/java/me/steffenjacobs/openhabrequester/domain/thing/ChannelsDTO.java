package me.steffenjacobs.openhabrequester.domain.thing;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"linkedItems","uid","channelTypeUID", "itemType","kind","label","description","defaultTags"})
public class ChannelsDTO {
	
	@JsonProperty("linkedItems")
	private List<Object> linkedItems = null;
	@JsonProperty("uid")
	private String uid; 
	@JsonProperty("id")
	private String id; 
	@JsonProperty("channelTypeUID")
	private String channelTypeUID; 
	@JsonProperty("itemType")
	private String itemType; 
	@JsonProperty("kind")
	private String kind; 
	@JsonProperty("label")
	private String label; 
	@JsonProperty("description")
	private String description; 
	@JsonProperty("defaultTags")
	private List<Object> defaultTags = null;
	
	@JsonAnyGetter
	public List<Object> getLinkedItems() {
		return linkedItems;
	}
	@JsonAnySetter
	public void setLinkedItems(List<Object> linkedItems) {
		this.linkedItems = linkedItems;
	}
	@JsonProperty("uid")
	public String getUid() {
		return uid;
	}
	@JsonProperty("uid")
	public void setUid(String uid) {
		this.uid = uid;
	}
	@JsonProperty("id")
	public String getId() {
		return id;
	}
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}
	@JsonProperty("channelTypeUID")
	public String getChannelTypeUID() {
		return channelTypeUID;
	}
	@JsonProperty("channelTypeUID")
	public void setChannelTypeUID(String channelTypeUID) {
		this.channelTypeUID = channelTypeUID;
	}
	@JsonProperty("itemType")
	public String getItemType() {
		return itemType;
	}
	@JsonProperty("itemType")
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	@JsonProperty("kind")
	public String getKind() {
		return kind;
	}
	@JsonProperty("kind")
	public void setKind(String kind) {
		this.kind = kind;
	}
	@JsonProperty("label")
	public String getLabel() {
		return label;
	}
	@JsonProperty("label")
	public void setLabel(String label) {
		this.label = label;
	}
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonAnyGetter
	public List<Object> getDefaultTags() {
		return defaultTags;
	}
	@JsonAnySetter
	public void setDefaultTags(List<Object> defaultTags) {
		this.defaultTags = defaultTags;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannelsDTO [linkedItems=");
		builder.append(linkedItems);
		builder.append(", uid=");
		builder.append(uid);
		builder.append(", id=");
		builder.append(id);
		builder.append(", channelTypeUID=");
		builder.append(channelTypeUID);
		builder.append(", itemType=");
		builder.append(itemType);
		builder.append(", kind=");
		builder.append(kind);
		builder.append(", label=");
		builder.append(label);
		builder.append(", description=");
		builder.append(description);
		builder.append(", defaultTags=");
		builder.append(defaultTags);
		builder.append("]");
		return builder.toString();
	}
	
}
