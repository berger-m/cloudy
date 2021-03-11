package com.cloudy.web.integration;

import static com.cloudy.web.utils.CustomAsserts.assertClimatePageLoadSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudy.business.entities.repositories.EntryRepository;
import com.cloudy.web.BaseClimateSpringBootTest;
import com.cloudy.web.controller.AbstractClimateController;
import com.cloudy.web.controller.ClimateSummaryController;
import com.cloudy.web.pages.ClimateSummaryPage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClimateSummaryTests extends BaseClimateSpringBootTest {

	@LocalServerPort
	private int port;

	private UriComponents baseUrl;

	@MockBean
	private EntryRepository entryRepo;

	private WebDriver driver;

	@BeforeEach
	void setup(final WebApplicationContext context) {
		driver = MockMvcHtmlUnitDriverBuilder.webAppContextSetup(context).build();
		baseUrl = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port)
				.pathSegment(AbstractClimateController.SEGMENT, ClimateSummaryController.SEGMENT).build();
		driver.get(baseUrl.toString());
	}

	/**
	 * Uses the mock Repository to simulate running against an empty DB
	 */
	@Test
	void successPathPageLoadNoEntriesTest() {
		Mockito.when(entryRepo.findAll()).thenReturn(Collections.emptyList());
		final int expectedNumOfRows = 0;
		final ClimateSummaryPage page = ClimateSummaryPage.to(driver);
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);

		final List<WebElement> elements = driver.findElements(By.id("tableSummaryIsEmptyMsg"));
		assertNotNull(elements);
		assertFalse(elements.isEmpty());
		assertEquals(expectedNumOfRows, page.getTableSummary().length);
	}

	@AfterEach
	void destroy() {
		if (driver != null) {
			driver.close();
		}
	}

}
