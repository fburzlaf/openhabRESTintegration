package me.steffenjacobs.openhabrequester.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import me.steffenjacobs.openhabrequester.domain.links.LinksDTO;

/** @author Steffen Jacobs */
public class TestOpenHabLinksService {

	@Test
	public void testLinksService() throws UnsupportedEncodingException {

		// request links
		List<LinksDTO> list = new OpenHabLinksService().requestLinks("http://localhost:8080");
		Assert.assertEquals(0, list.size());

		// Create link
		boolean created = new OpenHabLinksService().createLink("http://localhost:8080", URLEncoder.encode("astro:moon:home:position#azimuth", "UTF-8"),
				URLEncoder.encode("Moon_Azimuth", "UTF-8"), "testbody");
		Assert.assertTrue(created);

		// request links again
		list = new OpenHabLinksService().requestLinks("http://localhost:8080");
		Assert.assertEquals(1, list.size());

		// request single link
		LinksDTO link = new OpenHabLinksService().requestLinksByChannelUidAndItemName("http://localhost:8080", URLEncoder.encode("astro:moon:home:position#azimuth", "UTF-8"),
				URLEncoder.encode("Moon_Azimuth", "UTF-8"));
		Assert.assertNotNull(link);

		// delete link
		boolean deleted = new OpenHabLinksService().deleteLink("http://localhost:8080", URLEncoder.encode("astro:moon:home:position#azimuth", "UTF-8"),
				URLEncoder.encode("Moon_Azimuth", "UTF-8"));
		Assert.assertTrue(deleted);

		// request links again
		list = new OpenHabLinksService().requestLinks("http://localhost:8080");
		Assert.assertEquals(0, list.size());
	}
}
