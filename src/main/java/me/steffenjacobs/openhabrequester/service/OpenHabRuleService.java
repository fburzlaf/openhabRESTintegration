package me.steffenjacobs.openhabrequester.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.steffenjacobs.openhabrequester.domain.rule.RuleDTO;
import me.steffenjacobs.openhabrequester.domain.rule.RuleNameDTO;
import me.steffenjacobs.openhabrequester.domain.rule.RuleNamesDTO;

/** @author Steffen Jacobs */
public final class OpenHabRuleService {

	private final OpenHabSharedService sharedService = new OpenHabSharedService();

	public List<RuleDTO> requestRules(String openHabUrlWithPort,List<NameValuePair> parameters) {
		return requestAllRules(openHabUrlWithPort, parameters, requestRuleNames(openHabUrlWithPort));
	}

	public RuleNamesDTO requestRuleNames(String openHabUrlWithPort) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/habmin/rules"), new TypeReference<RuleNamesDTO>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<RuleDTO> requestAllRules(String openHabUrlWithPort, List<NameValuePair> parameters, RuleNamesDTO ruleNames) {
		List<RuleDTO> result = new ArrayList<>();
		if (ruleNames == null) {
			return result;
		}

		for (RuleNameDTO ruleName : ruleNames.getRules()) {
			final RuleDTO requestedRule = requestRule(openHabUrlWithPort, ruleName.getName());
			if (requestedRule != null) {
				result.add(requestedRule);
			}
		}

		return result;

	}

	public RuleDTO requestRule(String openHabUrlWithPort, String modelName) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new URL(openHabUrlWithPort + "/rest/habmin/rules" + "/" + modelName), new TypeReference<RuleDTO>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean createOrUpdateRule(String openHabUrlWithPort, List<NameValuePair> parameters, RuleDTO rule) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String ruleJson = objectMapper.writeValueAsString(rule);
			return sharedService.sendPut(openHabUrlWithPort + "/rest/habmin/rules" + "/" + rule.getName(), parameters, ruleJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
