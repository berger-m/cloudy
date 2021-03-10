package com.cloudy.web.pages;

import java.util.List;

import javax.swing.text.html.HTML;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClimateStationDetailsPage extends AbstractClimatePage {
	
	public static final String PAGE_TITLE = "Detailed Climate Records for Station";	
	public static final int DETAILS_TABLE_WIDTH = 6;
	public static final int DETAILS_TABLE_COLUMN_STATION_NAME = 0;
	public static final int DETAILS_TABLE_COLUMN_PROVINCE = 1;
	public static final int DETAILS_TABLE_COLUMN_DATE = 2;
	public static final int DETAILS_TABLE_COLUMN_MEAN_TEMP = 3;
	public static final int DETAILS_TABLE_COLUMN_HMMT = 4;
	public static final int DETAILS_TABLE_COLUMN_LMMT = 5;
	
	@FindBy(id = "stationDetailsBackBtn")
	private WebElement backBtn;
	
	@FindBy(id = "tableDetailsIsEmptyMsg")
	private WebElement tableDetailsIsEmptyMsg;
		
	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableDetailsFieldStationName')]"))
	private List<WebElement> tableDetailsColumnStationName;
	
	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableDetailsFieldProv')]"))
	private List<WebElement> tableDetailsColumnProvince;
	
	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableDetailsFieldDate')]"))
	private List<WebElement> tableDetailsColumnDate;
	
	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableDetailsFieldMeanTemp')]"))
	private List<WebElement> tableDetailsColumnMeanTemp;
	
	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableDetailsFieldHmmtRow')]"))
	private List<WebElement> tableDetailsColumnHmmt;
	
	@FindAll(@FindBy(xpath = "//*[starts-with(@id,'tableDetailsFieldLmmtRow')]"))
	private List<WebElement> tableDetailsColumnLmmt;
	
	private WebElement[][] tableDetails = null;
	
	public ClimateStationDetailsPage(WebDriver paramDriver) {
        super (paramDriver);
    }
	
	private void init () {
		if ( isNull(tableDetailsColumnStationName) || isNull(tableDetailsColumnProvince) || isNull(tableDetailsColumnDate) || isNull(tableDetailsColumnMeanTemp) || isNull(tableDetailsColumnHmmt) || isNull(tableDetailsColumnLmmt) //
				|| tableDetailsColumnStationName.isEmpty() || tableDetailsColumnProvince.isEmpty() || tableDetailsColumnDate.isEmpty() || tableDetailsColumnMeanTemp.isEmpty() || tableDetailsColumnHmmt.isEmpty() || tableDetailsColumnLmmt.isEmpty() ) {
			tableDetails = new WebElement[][] {};
			return;
		}
			
		// TODO validate that all the columns are the same length
		final int tableRows = tableDetailsColumnStationName.size();
		tableDetails = new WebElement [tableRows][DETAILS_TABLE_WIDTH];
		for ( int i = 0; i < tableRows; i++ ) {
			final WebElement[] row = new WebElement[] {tableDetailsColumnStationName.get(i), tableDetailsColumnProvince.get(i), tableDetailsColumnDate.get(i), tableDetailsColumnMeanTemp.get(i), tableDetailsColumnHmmt.get(i), tableDetailsColumnLmmt.get(i)};
			tableDetails[i] = row;
		}
	}
	
	public WebElement[][] getTableDetails () {
		return tableDetails;
	}
	
	public ClimateSummaryPage clickBackButton() {
		backBtn.click();		
		return ClimateSummaryPage.to(getDriver());
	}
	
	public static ClimateStationDetailsPage to(WebDriver driver) {
		final ClimateStationDetailsPage page = PageFactory.initElements(driver, ClimateStationDetailsPage.class);
		page.init();
        return page;
    }
}
