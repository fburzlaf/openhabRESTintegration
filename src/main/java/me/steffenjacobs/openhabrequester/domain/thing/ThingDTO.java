package me.steffenjacobs.openhabrequester.domain.thing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"label","bridgeUID","UID","thingTypeUID","location","editable", "channels"})
public class ThingDTO{

	@JsonProperty("label")
	private String label;
	@JsonProperty("bridgeUID")
	private String bridgeUID;
	@JsonProperty("UID")
	private String UID;
	@JsonProperty("thingTypeUID")
	private String thingTypeUID;
	@JsonProperty("location")
	private String location;
	@JsonProperty("editable")
	private String editable;
	@JsonProperty("channels")
	private List<ChannelsDTO> channelsDTO = null;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("label")
	public String getLabel() {
		return label;
	}

	@JsonProperty("label")
	public void setLabel(String label) {
		this.label = label;
	}

	@JsonProperty("bridgeUID")
	public String getBridgeUID() {
		return bridgeUID;
	}

	@JsonProperty("bridgeUID")
	public void setBridgeUID(String bridgeUID) {
		this.bridgeUID = bridgeUID;
	}

	@JsonProperty("UID")
	public String getUID() {
		return UID;
	}

	@JsonProperty("UID")
	public void setUID(String uID) {
		UID = uID;
	}

	@JsonProperty("thingTypeUID")
	public String getThingTypeUID() {
		return thingTypeUID;
	}

	@JsonProperty("thingTypeUID")
	public void setThingTypeUID(String thingTypeUID) {
		this.thingTypeUID = thingTypeUID;
	}

	@JsonProperty("location")
	public String getLocation() {
		return location;
	}

	@JsonProperty("location")
	public void setLocation(String location) {
		this.location = location;
	}

	@JsonProperty("editable")
	public String getEditable() {
		return editable;
	}

	@JsonProperty("editable")
	public void setEditable(String editable) {
		this.editable = editable;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	
	
	@JsonProperty("channels")
	public List<ChannelsDTO> getChannelsDTO() {
		return channelsDTO;
	}
	
	@JsonProperty("channels")
	public void setChannelsDTO(List<ChannelsDTO> channelsDTO) {
		this.channelsDTO = channelsDTO;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ThingDTO [label=");
		builder.append(label);
		builder.append(", bridgeUID=");
		builder.append(bridgeUID);
		builder.append(", UID=");
		builder.append(UID);
		builder.append(", thingTypeUID=");
		builder.append(thingTypeUID);
		builder.append(", location=");
		builder.append(location);
		builder.append(", editable=");
		builder.append(editable);
		builder.append(", additionalProperties=");
		builder.append(additionalProperties);
		builder.append("]");
		return builder.toString();
	}
	
}
