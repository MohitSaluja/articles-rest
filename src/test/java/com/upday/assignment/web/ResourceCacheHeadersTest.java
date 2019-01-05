package com.upday.assignment.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceCacheHeadersTest {

	@LocalServerPort
	private int serverPort;

	@Test
	public void whenGetRequestForCategories_shouldRespondMaxAgeCacheControl() {
		MockMvcRequestBuilders.get(getBaseUrl() + "/articles").header("Cache-Control", "max-age=60");
	}

	@Test
	public void whenGetRequestForCategories_shouldReturnApplicationJSONAndMaxAgeCacheControl() {
		MockMvcRequestBuilders.get(getBaseUrl() + "/articles").contentType(MediaType.APPLICATION_JSON).header("Cache-Control", "max-age=60");
	}

	private String getBaseUrl() {
		return String.format("http://localhost:%d", serverPort);
	}

}