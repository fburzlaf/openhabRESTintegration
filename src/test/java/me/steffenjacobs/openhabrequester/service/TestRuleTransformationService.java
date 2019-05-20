package me.steffenjacobs.openhabrequester.service;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/** @author Steffen Jacobs */
public class TestRuleTransformationService {
	
	@Test
	public void testGetItemNamesFromRule(){
		
		final String ruleSource = "rule \"AmbientInsideJSON Rule\"\r\n" + 
				"when        \r\n" + 
				"                Item AmbientInsideJSON changed\r\n" + 
				"then\r\n" + 
				"                val String json = (AmbientInsideJSON.state as StringType).toString\r\n" + 
				"                val value_temperature = transform(\"JSONPATH\", \"$.temperature\", json)\r\n" + 
				"                TemperatureInside.postUpdate(value_temperature)\r\n" + 
				"                val value_humidity = transform(\"JSONPATH\", \"$.humidity\", json)\r\n" + 
				"                HumidityInside.postUpdate(value_humidity)\r\n" + 
				"end";
		
		Set<String> itemNamesFromRule = new RuleTransformationService().getItemNamesFromRule(ruleSource);
		
		Assert.assertEquals(3, itemNamesFromRule.size());
		Assert.assertTrue(itemNamesFromRule.contains("AmbientInsideJSON"));
		Assert.assertTrue(itemNamesFromRule.contains("TemperatureInside"));
		Assert.assertTrue(itemNamesFromRule.contains("HumidityInside"));
	}

}
