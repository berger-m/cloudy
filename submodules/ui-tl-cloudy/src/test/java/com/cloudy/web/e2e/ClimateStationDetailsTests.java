package com.cloudy.web.e2e;

import static com.cloudy.web.utils.CustomAsserts.assertClimatePageLoadSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cloudy.web.BaseClimateSpringBootTest;
import com.cloudy.web.controller.ClimateController;
import com.cloudy.web.controller.ClimateDetailsController;
import com.cloudy.web.controller.ClimateSummaryController;
import com.cloudy.web.data.TestData;
import com.cloudy.web.pages.ClimateStationDetailsPage;
import com.cloudy.web.pages.ClimateSummaryPage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClimateStationDetailsTests extends BaseClimateSpringBootTest {

	@LocalServerPort
	private int port;

	private UriComponents baseUrl;
	// private URL baseUrl;

	private WebDriver driver;

	@BeforeEach
	void setup(final WebApplicationContext context) {
		driver = MockMvcHtmlUnitDriverBuilder.webAppContextSetup(context).build();
		baseUrl = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port).build();
	}

	/**
	 * 
	 */
	@Test
	void successPathNavigateToStationDetailsTest() {

		// Navigate to the stationDetails page
		final String climateSummaryUrl = UriComponentsBuilder.fromUri(baseUrl.toUri())
				.pathSegment(ClimateController.SEGMENT, ClimateSummaryController.SEGMENT).build().toUriString();
		driver.get(climateSummaryUrl);
		ClimateSummaryPage page = ClimateSummaryPage.to(driver);
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);
		final int numOfRecordsInDateRange = 2;
		final LocalDate newToFromDate = TestData.DATE_OF_NORTH_COWICHAN_BC;
		page.setFromToDate(newToFromDate, newToFromDate);
		page = page.clickFilterButton();
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);
		assertEquals(numOfRecordsInDateRange, page.getTableSummary().length, //
				"Unexpected number of climate records after filtering by dates in range (from="
						+ ClimateSummaryPage.DATE_FORMATTER.format(newToFromDate) + ", to= "
						+ ClimateSummaryPage.DATE_FORMATTER.format(newToFromDate) + ")");

		final String expectedStationName = TestData.NAME_OF_STATION_NORTH_COWICHAN_BC;
		final String actualStationName = page.getTableSummary()[0][ClimateSummaryPage.SUMMARY_TABLE_COLUMN_STATION_NAME]
				.getText();
		assertEquals(expectedStationName, actualStationName);
		WebElement linkToStationDetailsPage = page
				.getTableSummary()[0][ClimateSummaryPage.SUMMARY_TABLE_COLUMN_MEAN_TEMP];

		// Arrived at the stationDetails page
		final ClimateStationDetailsPage stationPage = page.visitStationDetailsPage(linkToStationDetailsPage);
		assertNotNull(stationPage);
		assertClimatePageLoadSuccess(ClimateStationDetailsPage.PAGE_TITLE, driver);

		final List<WebElement> elements = driver.findElements(By.id("tableDetailsIsEmptyMsg"));
		assertNotNull(elements);
		assertTrue(elements.isEmpty());
		assertEquals(TestData.NUM_OF_NORTH_COWICHAN_BC_ENTRIES, stationPage.getTableDetails().length);

	}

	@Test
	void successPathBackToSummaryTest() {
		final Long invalidStationId = Long.valueOf(-1);
		final String climateStationDetailsInvalidUrl = UriComponentsBuilder.fromUri(baseUrl.toUri())
				.pathSegment(ClimateController.SEGMENT, ClimateDetailsController.SEGMENT, invalidStationId.toString())
				.build().toUriString();
		driver.get(climateStationDetailsInvalidUrl);
		final ClimateStationDetailsPage stationPage = ClimateStationDetailsPage.to(driver);
		assertNotNull(stationPage);
		assertClimatePageLoadSuccess(ClimateStationDetailsPage.PAGE_TITLE, driver);

		ClimateSummaryPage summaryPage = stationPage.clickBackButton();
		final int expectedNumOfRows = TestData.TOTAL_ROWS;
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);
		assertEquals(expectedNumOfRows, summaryPage.getTableSummary().length);
	}

	@Test
	void errorPathInvalidStationIdParamTest() {
		final Long invalidStationId = Long.valueOf(-1);
		final String climateStationDetailsInvalidUrl = UriComponentsBuilder.fromUri(baseUrl.toUri())
				.pathSegment(ClimateController.SEGMENT, ClimateDetailsController.SEGMENT, invalidStationId.toString())
				.build().toUriString();
		driver.get(climateStationDetailsInvalidUrl);
		final ClimateStationDetailsPage stationPage = ClimateStationDetailsPage.to(driver);
		assertNotNull(stationPage);
		assertClimatePageLoadSuccess(ClimateStationDetailsPage.PAGE_TITLE, driver);

		// TODO instead of showing the table-is-empty-message (and sample data) provide
		// a message indicating that the stationId is invalid
		final List<WebElement> elements = driver.findElements(By.id("tableDetailsIsEmptyMsg"));
		assertNotNull(elements);
		assertFalse(elements.isEmpty());
	}

	@AfterEach
	void destroy() {
		if (driver != null) {
			driver.close();
		}
	}

}
