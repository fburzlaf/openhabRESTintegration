package me.steffenjacobs.openhabrequester.service.generation;

import java.util.Map.Entry;

import me.steffenjacobs.openhabrequester.domain.MqttRuleConfiguration;
import me.steffenjacobs.openhabrequester.domain.MqttRuleConfiguration.MqttCommand;
import me.steffenjacobs.openhabrequester.domain.item.JSONItem;
import me.steffenjacobs.openhabrequester.domain.item.creation.ItemCreationDTO;

/** @author Steffen Jacobs */
public class RuleGenerationService {

	public String generateMqttPublishRule(String ruleName, MqttRuleConfiguration ruleConfiguration) {
		StringBuilder builder = new StringBuilder();
		builder.append("rule \"");
		builder.append(ruleName);
		builder.append("\"\nwhen        ");
		// TODO: condition
		builder.append("\nthen\n        ");
		if (ruleConfiguration.getMqttCommand() == MqttCommand.PUBLISH) {
			builder.append("publish(\"");
			builder.append(ruleConfiguration.getBrokerName());
			builder.append("\", \"");
			builder.append(ruleConfiguration.getMqttChannel());
			builder.append("\", \"");
			builder.append(ruleConfiguration.getMessage());
			builder.append("\")");
		}
		builder.append("\nend");

		return builder.toString();
	}

	private static void generateTestMqttRule() {
		MqttRuleConfiguration ruleConfiguration = new MqttRuleConfiguration("broker", "/devices/DenonAVR",
				"{\"type\": \"request\", \"request\": {\"device\": \"DenonAVR\", \"sendState\": \"SEND_ONCE\", \"command\": \"KEY_MUTE\"}}", MqttCommand.PUBLISH);
		String rule = new RuleGenerationService().generateMqttPublishRule("Test-Rule-Name", ruleConfiguration);
		System.out.println(rule);
	}

	public String generateRule(String ruleName, JSONItem jsonItem) {
		StringBuilder builder = new StringBuilder();
		builder.append("rule \"");
		builder.append(ruleName);
		builder.append("\"\nwhen        \n");

		builder.append("                Item ");
		builder.append(jsonItem.getName());
		builder.append(" changed\n");
		builder.append("then\n");
		builder.append("                val String json = (");
		builder.append(jsonItem.getName());
		builder.append(".state as StringType).toString\n");
		for (Entry<String, ItemCreationDTO> entry : jsonItem.getJsonMappings().entrySet()) {
			builder.append("                val value_");
			builder.append(entry.getKey());
			builder.append(" = transform(\"JSONPATH\", \"$.");
			builder.append(entry.getKey());
			builder.append("\", json)\n");
			builder.append("                ");
			builder.append(entry.getValue().getName());
			builder.append(".postUpdate(value_");
			builder.append(entry.getKey());
			builder.append(")\n");
		}
		builder.append("end");

		return builder.toString();
	}
}
