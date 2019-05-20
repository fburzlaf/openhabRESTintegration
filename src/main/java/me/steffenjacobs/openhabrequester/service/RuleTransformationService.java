package me.steffenjacobs.openhabrequester.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Steffen Jacobs */
public class RuleTransformationService {
	private static final Pattern ITEM_FINDER_FOR_JSON_RULES = Pattern.compile("\\s*([a-zA-Z0-9]+)\\.postUpdate");
	private static final Pattern ITEM_FINDER_FOR_JSON_RULES_2 = Pattern.compile(".*\\(([a-zA-Z0-9]+)\\.state.*");

	/**
	 * Parses all items which are updated or of which the state is received from the
	 * {@link #ruleSource} by using regular expression.
	 * 
	 * @return A set of item names found in the rule.
	 */
	public Set<String> getItemNamesFromRule(String ruleSource) {
		Set<String> result = new HashSet<>();

		Matcher m = ITEM_FINDER_FOR_JSON_RULES.matcher(ruleSource);
		while (m.find()) {
			result.add(m.group(1));
		}
		m = ITEM_FINDER_FOR_JSON_RULES_2.matcher(ruleSource);
		while (m.find()) {
			result.add(m.group(1));
		}

		return result;
	}

}
