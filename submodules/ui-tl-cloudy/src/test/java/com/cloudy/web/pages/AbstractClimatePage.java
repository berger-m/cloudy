package com.cloudy.web.pages;

import org.openqa.selenium.WebDriver;

public class AbstractClimatePage {
	
	private WebDriver driver;
		
	public AbstractClimatePage(WebDriver paramDriver) {
        setDriver(paramDriver);
    }

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
