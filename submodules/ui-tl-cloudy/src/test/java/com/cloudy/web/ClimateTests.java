package com.cloudy.web;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudy.web.controller.AbstractClimateController;
import com.cloudy.web.controller.ClimateSummaryController;

@AutoConfigureMockMvc
public class ClimateTests extends BaseClimateSpringBootTest {

	private static final UriComponents summaryPath = UriComponentsBuilder
			.fromPath("/" + AbstractClimateController.SEGMENT) //
			.pathSegment(ClimateSummaryController.SEGMENT).build();

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	void setup() {
	}

	/**
	 * Ensure that when a path is requested for a page that does not exist that the
	 * servlet responds with HTTP error code 404 (page not found)
	 */
	@Test
	void errorPathPageNotFoundTest() {
		assertDoesNotThrow(() -> mvc.perform(MockMvcRequestBuilders.get(summaryPath.toUriString() + "/pathToRubbish")
				.accept(MediaType.APPLICATION_XHTML_XML)).andExpect(status().isNotFound()));
	}

	@AfterEach
	void destroy() {
	}

}
