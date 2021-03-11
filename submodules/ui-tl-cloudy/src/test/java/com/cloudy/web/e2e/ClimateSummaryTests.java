package com.cloudy.web.e2e;

import static com.cloudy.web.utils.CustomAsserts.assertClimatePageLoadSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.cloudy.web.controller.ClimateSummaryController;
import com.cloudy.web.data.TestData;
import com.cloudy.web.pages.ClimateStationDetailsPage;
import com.cloudy.web.pages.ClimateSummaryPage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClimateSummaryTests extends BaseClimateSpringBootTest {

	@LocalServerPort
	private int port;

	private UriComponents baseUrl;


	private WebDriver driver;

	@BeforeEach
	void setup(final WebApplicationContext context) {
		driver = MockMvcHtmlUnitDriverBuilder.webAppContextSetup(context).build();
		baseUrl = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port)
				.pathSegment(ClimateController.SEGMENT, ClimateSummaryController.SEGMENT).build();
		driver.get(baseUrl.toString());
	}

	/**
	 * Just make sure the page loaded with expected test data set
	 */
	@Test
	void successPathPageLoadTest() {
		final int expectedNumOfRows = TestData.TOTAL_ROWS;
		final ClimateSummaryPage page = ClimateSummaryPage.to(driver);
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);

		final List<WebElement> elements = driver.findElements(By.id("tableSummaryIsEmptyMsg"));
		assertNotNull(elements);
		assertTrue(elements.isEmpty());
		assertEquals(expectedNumOfRows, page.getTableSummary().length);
	}

	/**
	 * This test ensures that the date filters are applied correctly
	 */
	@Test
	void successPathFilterByDatesTest() {
		ClimateSummaryPage page = ClimateSummaryPage.to(driver);
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);
		final int numOfRecordsInDateRange = 5;
		final LocalDate newFromDate = TestData.MIN_DATE.plusMonths(5);
		final LocalDate newToDate = TestData.MAX_DATE.minusMonths(5);
		page.setFromToDate(newFromDate, newToDate);
		page = page.clickFilterButton();
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);
		// Ensure that the number of rows provisioned does not accidentally happen to be
		// the same as the number found
		assertTrue(TestData.TOTAL_ROWS > numOfRecordsInDateRange, //
				"Expected to find fewer climate records after applying date filter");
		assertEquals(numOfRecordsInDateRange, page.getTableSummary().length, //
				"Unexpected number of climate records after filtering by dates in range (from="
						+ ClimateSummaryPage.DATE_FORMATTER.format(newFromDate) + ", to="
						+ ClimateSummaryPage.DATE_FORMATTER.format(newToDate) + ")");
	}

	/**
	 * This test filters to only display the records from the North Cowichan, BC
	 * station and then navigates to the details page (verification of the contents
	 * of the details page will be done in the ClimateStationDetailsTests suite)
	 */
	@Test
	void successPathNavigateToStationDetailsTest() {
		ClimateSummaryPage page = ClimateSummaryPage.to(driver);
		assertClimatePageLoadSuccess(ClimateSummaryPage.PAGE_TITLE, driver);
		final int numOfRecordsInDateRange = TestData.NUM_OF_NORTH_COWICHAN_BC_ENTRIES;
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
		final ClimateStationDetailsPage stationPage = page.visitStationDetailsPage(linkToStationDetailsPage);
		assertNotNull(stationPage);
		assertClimatePageLoadSuccess(ClimateStationDetailsPage.PAGE_TITLE, driver);
	}

	@AfterEach
	void destroy() {
		if (driver != null) {
			driver.close();
		}
	}

}
