package com.cloudy.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.openqa.selenium.WebDriver;

public class CustomAsserts {

	public static void assertClimatePageLoadSuccess(final String expectedPageTitle, final WebDriver driver) {
		final String actualPageSource = driver.getPageSource();
		assertNotNull(actualPageSource, "Expected that the page source would not be null");
		assertFalse(actualPageSource.isEmpty(), "Expected the page source to be present");

		final String actualPageTitle = driver.getTitle();
		assertNotNull(actualPageTitle, "Expected that the page source would not be null");
		assertEquals(expectedPageTitle, actualPageTitle,
				"The page's actual title did not match the expected page title");
	}
}
