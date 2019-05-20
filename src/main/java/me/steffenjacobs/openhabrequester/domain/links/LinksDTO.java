package me.steffenjacobs.openhabrequester.domain.links;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import me.steffenjacobs.openhabrequester.domain.thing.ConfigurationDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"channelUID","configuration","itemName"})
public class LinksDTO {
	
	@JsonProperty("channelUID")
	private String channelUID;
	@JsonProperty("itemName")
	private String itemName;
	@JsonProperty("configuration")
	private ConfigurationDTO configuration;
	
	@JsonProperty("channelUID")
	public String getChannelUID() {
		return channelUID;
	}
	@JsonProperty("channelUID")
	public void setChannelUID(String channelUID) {
		this.channelUID = channelUID;
	}
	@JsonProperty("itemName")
	public String getItemName() {
		return itemName;
	}
	@JsonProperty("itemName")
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@JsonProperty("configuration")
	public ConfigurationDTO getConfiguration() {
		return configuration;
	}
	@JsonProperty("configuration")
	public void setConfiguration(ConfigurationDTO configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LinksDTO [channelUID=");
		builder.append(channelUID);
		builder.append(", itemName=");
		builder.append(itemName);
		builder.append(", configuration=");
		builder.append(configuration);
		builder.append("]");
		return builder.toString();
	}
	
}
