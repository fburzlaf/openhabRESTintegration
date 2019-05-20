package me.steffenjacobs.openhabrequester.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.steffenjacobs.openhabrequester.domain.experimental.rule.Action;
import me.steffenjacobs.openhabrequester.domain.experimental.rule.Condition;
import me.steffenjacobs.openhabrequester.domain.experimental.rule.Configuration;
import me.steffenjacobs.openhabrequester.domain.experimental.rule.ExperimentalRule;
import me.steffenjacobs.openhabrequester.domain.experimental.rule.Trigger;

/** @author Steffen Jacobs */
public class OpenHabExperimentalRulesService {

	private final OpenHabSharedService sharedService = new OpenHabSharedService();

	public List<ExperimentalRule> requestAllRules(String openHabUrlWithPort) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/rules"), new TypeReference<List<ExperimentalRule>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public boolean createRule(String openHabUrlWithPort, ExperimentalRule rule) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return 201 == sharedService.sendPost(openHabUrlWithPort + "/rest/rules", objectMapper.writeValueAsString(rule));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateRuleById(String openHabUrlWithPort, ExperimentalRule rule, String uid) {
		if (!uid.equals(rule.getUid())) {
			throw new IllegalArgumentException("UID of rule (" + rule.getUid() + ") did not match given uid (" + uid + ")!");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return 200 == sharedService.sendPutWithPathParameters(openHabUrlWithPort + "/rest/rules/" + uid, objectMapper.writeValueAsString(rule));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteRule(String openHabUrlWithPort, String uid) {
		try {
			return sharedService.sendDelete(openHabUrlWithPort + "/rest/rules/" + uid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ExperimentalRule requestRuleByUid(String openHabUrlWithPort, String uid) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/rules/" + uid), new TypeReference<ExperimentalRule>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Action> getActionsFromRuleById(String openHabUrlWithPort, String uid) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/rules/" + uid + "/actions"), new TypeReference<List<Action>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<Condition> getConditionsFromRuleById(String openHabUrlWithPort, String uid) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/rules/" + uid + "/conditions"), new TypeReference<List<Condition>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Configuration getConfigFromRuleById(String openHabUrlWithPort, String uid) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/rules/" + uid + "/config"), new TypeReference<Configuration>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Trigger> getTriggersFromRuleById(String openHabUrlWithPort, String uid) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/rules/" + uid + "/triggers"), new TypeReference<List<Trigger>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public boolean updateConfigurationOfRuleById(String openHabUrlWithPort, String uid, Configuration configuration) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return 200 == sharedService.sendPutWithPathParameters(openHabUrlWithPort + "/rest/rules/" + uid + "/config", objectMapper.writeValueAsString(configuration));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean runRuleByIdNow(String openHabUrlWithPort, String uid) {
		try {
			return 200 == sharedService.sendPost(openHabUrlWithPort + "/rest/rules/" + uid + "/runnow", false, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean enableRuleById(String openHabUrlWithPort, String uid, String body) {
		try {
			return 200 == sharedService.sendPost(openHabUrlWithPort + "/rest/rules/" + uid + "/enable", false, body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
