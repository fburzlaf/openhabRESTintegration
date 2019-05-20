package me.steffenjacobs.openhabrequester.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import me.steffenjacobs.openhabrequester.Main;

/** @author Steffen Jacobs */
public class TestOpenHabValueRetrieverService {

	
	@Test
	public void testGetValueFromItemByName() {
		String valueFromItemByName = new OpenHabValueRetrieverService().getValueFromItemByName("TemperatureInside", Main.OPEN_HAB_URL_WITH_PORT);
		assertNotNull(valueFromItemByName);
	}
}
