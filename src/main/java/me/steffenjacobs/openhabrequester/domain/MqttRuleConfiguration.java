package me.steffenjacobs.openhabrequester.domain;

/** @author Steffen Jacobs */
public class MqttRuleConfiguration {

	public static enum MqttCommand {
		PUBLISH, SUBSCRIBE;
	}

	private String brokerName;
	private String mqttChannel;
	private String message;
	private MqttCommand mqttCommand;

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getMqttChannel() {
		return mqttChannel;
	}

	public void setMqttChannel(String mqttChannel) {
		this.mqttChannel = mqttChannel;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MqttCommand getMqttCommand() {
		return mqttCommand;
	}

	public void setMqttCommand(MqttCommand mqttCommand) {
		this.mqttCommand = mqttCommand;
	}

	public MqttRuleConfiguration(String brokerName, String mqttChannel, String message, MqttCommand mqttCommand) {
		this.brokerName = brokerName;
		this.mqttChannel = mqttChannel;
		this.message = message;
		this.mqttCommand = mqttCommand;
	}

	public MqttRuleConfiguration() {
	}

}
