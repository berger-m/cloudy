package com.cloudy.web.pages;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.text.html.HTML;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cloudy.web.controller.ClimateSummaryController;

/**
 * This page represents the contents of the
 * {@code resources/templates/summary.html} template.
 * 
 * @see ClimateSummaryController
 */
public class ClimateSummaryPage extends AbstractClimatePage {

	public static final String PAGE_TITLE = "Summary of Climate Records";
	public static final int SUMMARY_TABLE_WIDTH = 3;
	public static final int SUMMARY_TABLE_COLUMN_STATION_NAME = 0;
	public static final int SUMMARY_TABLE_COLUMN_DATE = 1;
	public static final int SUMMARY_TABLE_COLUMN_MEAN_TEMP = 2;

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

	@FindBy(id = "inputFilterParamFromDate")
	private WebElement inputFormFromDateField;

	@FindBy(id = "inputFilterParamToDate")
	private WebElement inputFormToDateField;

	@FindBy(id = "inputFilterParamSubmitBtn")
	private WebElement inputFilterSubmitBtn;

	@FindBy(id = "tableSummaryIsEmptyMsg")
	private WebElement tableSummaryIsEmptyMsg;

	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableSummaryFieldStationName')]"))
	private List<WebElement> tableSummaryColumnStationName;

	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableSummaryFieldDate')]"))
	private List<WebElement> tableSummaryColumnDate;

	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableSummaryFieldMeanTemp')]"))
	private List<WebElement> tableSummaryColumnMeanTemp;

	/**  */
	private WebElement[][] tableSummary = null;

	public ClimateSummaryPage(WebDriver paramDriver) {
		super(paramDriver);
	}

	private void init() {
		if (isNull(tableSummaryColumnStationName) || isNull(tableSummaryColumnDate)
				|| isNull(tableSummaryColumnMeanTemp) //
				|| tableSummaryColumnStationName.isEmpty() || tableSummaryColumnDate.isEmpty()
				|| tableSummaryColumnMeanTemp.isEmpty()) {
			tableSummary = new WebElement[][] {};
			return;
		}

		// TODO validate that all the columns are the same length
		final int tableRows = tableSummaryColumnStationName.size();
		tableSummary = new WebElement[tableRows][SUMMARY_TABLE_WIDTH];
		for (int i = 0; i < tableRows; i++) {
			final WebElement[] row = new WebElement[] { tableSummaryColumnStationName.get(i),
					tableSummaryColumnDate.get(i), tableSummaryColumnMeanTemp.get(i) };
			tableSummary[i] = row;
		}
	}

	public WebElement[][] getTableSummary() {
		return tableSummary;
	}

	public WebElement getTableIsEmptyMsg() {
		return tableSummaryIsEmptyMsg;
	}

	public void setFromDate(final LocalDate fromDate) {
		final String fromDateStr = DATE_FORMATTER.format(fromDate);
		inputFormFromDateField.clear();
		inputFormFromDateField.sendKeys(fromDateStr);
	}

	public void setToDate(final LocalDate toDate) {
		final String fromDateStr = DATE_FORMATTER.format(toDate);
		inputFormToDateField.clear();
		inputFormToDateField.sendKeys(fromDateStr);
	}

	public void setFromToDate(final LocalDate fromDate, final LocalDate toDate) {
		setFromDate(fromDate);
		setToDate(toDate);
	}

	public ClimateSummaryPage clickFilterButton() {
		inputFilterSubmitBtn.click();
		return to(getDriver());
	}

	public ClimateStationDetailsPage visitStationDetailsPage(final WebElement linkToStationDetailsPage) {
		linkToStationDetailsPage.click();
		return ClimateStationDetailsPage.to(getDriver());
	}

	public String getFromDate() {
		return inputFormFromDateField.getAttribute(HTML.Attribute.VALUE.toString());
	}

	public static ClimateSummaryPage to(WebDriver driver) {
		final ClimateSummaryPage page = PageFactory.initElements(driver, ClimateSummaryPage.class);
		page.init();
		return page;
	}
}
