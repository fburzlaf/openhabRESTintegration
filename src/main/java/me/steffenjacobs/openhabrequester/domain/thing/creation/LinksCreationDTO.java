package me.steffenjacobs.openhabrequester.domain.thing.creation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "itemName", "channelUID", "body"})
public class LinksCreationDTO {
	
	@JsonProperty("itemName")
	private String itemName;
	@JsonProperty("channelUID")
	private String channeUID;
	@JsonProperty("body")
	private String body;
	
	@JsonProperty("itemName")
	public String getItemName() {
		return itemName;
	}
	@JsonProperty("itemName")
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@JsonProperty("channeUID")
	public String getChanneUID() {
		return channeUID;
	}
	@JsonProperty("channeUID")
	public void setChanneUID(String channeUID) {
		this.channeUID = channeUID;
	}
	@JsonProperty("body")
	public String getBody() {
		return body;
	}
	@JsonProperty("body")
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LinksDTO [itemName=");
		builder.append(itemName);
		builder.append(", channeUID=");
		builder.append(channeUID);
		builder.append(", body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}

}
