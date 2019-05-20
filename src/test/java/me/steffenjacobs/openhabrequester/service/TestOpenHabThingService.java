package me.steffenjacobs.openhabrequester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.junit.Assert;
import org.junit.Test;

import me.steffenjacobs.openhabrequester.domain.thing.ThingDTO;

public class TestOpenHabThingService {

	@Test
	public void test() {
//		fail("Not yet implemented");
		
		OpenHabThingService service = new OpenHabThingService();
		
		
		ThingDTO dto = new ThingDTO();
		
		dto.setThingTypeUID("hue:0820");
		dto.setUID("hue:0820:a4dc70ek");
		
		List<ThingDTO> requestThings = service.requestThings("http://localhost:8080");
		System.out.println("size:0 " + requestThings.size());
		int createThing = service.createThing("http://localhost:8080",  dto);
		
		Assert.assertEquals(createThing, 201);
		
		List<ThingDTO> requestThings2 = service.requestThings("http://localhost:8080");
		
		Assert.assertTrue(requestThings.size() + 1 == requestThings2.size());
		
//		Invalid UTF-8 start byte 0xfc
//		Optional<ThingDTO> requestThingByName = service.requestThingByName("http://localhost:8080", "hue:0820:a4dc70ek");
		
//		Assert.assertTrue(requestThingByName.isPresent());
		
		dto.setLabel("MyUpdatedLabel");
		
		Assert.assertTrue(requestThings.stream().filter(thingdto -> thingdto.getLabel().equals("MyUpdatedLabel")).collect(Collectors.toList()).size() == 0);
		
		service.updateThing("http://localhost:8080", new ArrayList<NameValuePair>(), dto, "hue:0820:a4dc70ek");
		
		List<ThingDTO> requestThings1 = service.requestThings("http://localhost:8080");
		
		Assert.assertTrue(requestThings1.stream().filter(thingdto -> thingdto.getLabel().equals("MyUpdatedLabel")).collect(Collectors.toList()).size() == 1);
		
		String thingState = service.getThingState("http://localhost:8080", "hue:0820:a4dc70ek");
		System.out.println(thingState);
		
		boolean updateThingState = service.updateThingState("http://localhost:8080", "hue:0820:a4dc70ek", "DISABLE");
		
		Assert.assertTrue(updateThingState);
		

		String thingState1 = service.getThingState("http://localhost:8080", "hue:0820:a4dc70ek");
		
		Assert.assertTrue(thingState1.contains("DISABLED"));
		
		System.out.println(thingState1);
		
		boolean deleteThing = service.deleteThing("http://localhost:8080", "hue:0820:a4dc70ek");		
		Assert.assertTrue(deleteThing);	
		List<ThingDTO> requestThings3 = service.requestThings("http://localhost:8080");
		System.out.println(requestThings3.size());
		Assert.assertEquals(requestThings.size(), requestThings3.size());
	}

}
