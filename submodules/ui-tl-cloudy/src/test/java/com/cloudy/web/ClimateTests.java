package com.cloudy.web;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudy.business.entities.repositories.EntryRepository;
import com.cloudy.web.controller.AbstractClimateController;
import com.cloudy.web.controller.ClimateSummaryController;
import com.cloudy.web.ui.entities.FilterParams;

@AutoConfigureMockMvc
public class ClimateTests extends BaseClimateSpringBootTest {

	private static final UriComponents summaryPath = UriComponentsBuilder
			.fromPath("/" + AbstractClimateController.SEGMENT) //
			.pathSegment(ClimateSummaryController.SEGMENT).build();

	@MockBean
	private EntryRepository entryRepo;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	void setup() {
	}

	private static String getFilterFormParamName(final String elementName) {
		return "filterParams['" + elementName + "']";
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

	@Test
	void errorPathFilterFromDateBlankTest() throws Exception {

		final String expectedFromDate = "0001-01-01";
		final String expectedToDate = "2020-03-03";
		Mockito.when(entryRepo.findAllByDateBetween(Mockito.any(), Mockito.any())) //
				.thenReturn(Collections.emptyList());

		final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(summaryPath.toUriString()) //
				.accept(MediaType.APPLICATION_XHTML_XML) //
				.contentType(MediaType.APPLICATION_FORM_URLENCODED) //
				.param(getFilterFormParamName(FilterParams.KEY_FROM_DATE), "") //
				.param(getFilterFormParamName(FilterParams.KEY_TO_DATE), expectedToDate)).andExpect(status().isOk())
				.andReturn();
		final Object actualObj = result.getModelAndView().getModelMap().getAttribute("filterDateRange");
		assertNotNull(actualObj);
		assertTrue(actualObj instanceof FilterParams);
		final FilterParams actualFilterParams = (FilterParams) actualObj;
		assertEquals(expectedFromDate, actualFilterParams.getFilterParams().get(FilterParams.KEY_FROM_DATE));
		assertEquals(expectedToDate, actualFilterParams.getFilterParams().get(FilterParams.KEY_TO_DATE));
	}

	@Test
	void errorPathFilterToDateBlankTest() throws Exception {
		final String expectedFromDate = "2020-03-01";
		final String expectedToDate = "9999-01-01";
		Mockito.when(entryRepo.findAllByDateBetween(Mockito.any(), Mockito.any())) //
				.thenReturn(Collections.emptyList());

		final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(summaryPath.toUriString()) //
				.accept(MediaType.APPLICATION_XHTML_XML) //
				.contentType(MediaType.APPLICATION_FORM_URLENCODED) //
				.param(getFilterFormParamName(FilterParams.KEY_FROM_DATE), expectedFromDate) //
				.param(getFilterFormParamName(FilterParams.KEY_TO_DATE), "")) //
				.andExpect(status().isOk()) //
				.andReturn();
		final Object actualObj = result.getModelAndView().getModelMap().getAttribute("filterDateRange");
		assertNotNull(actualObj);
		assertTrue(actualObj instanceof FilterParams);
		final FilterParams actualFilterParams = (FilterParams) actualObj;
		assertEquals(expectedFromDate, actualFilterParams.getFilterParams().get(FilterParams.KEY_FROM_DATE));
		assertEquals(expectedToDate, actualFilterParams.getFilterParams().get(FilterParams.KEY_TO_DATE));
	}

	@AfterEach
	void destroy() {
	}

}
