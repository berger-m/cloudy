package com.cloudy.web;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.cloudy.web.application.ClimateApplication;
import com.cloudy.web.application.ConfigProperties;

@SpringBootTest
@ContextConfiguration(classes = {ClimateApplication.class})
public class BaseClimateSpringBootTest {
	
	static {
		// A selected subset of the provisioning data for testing purposes
		System.setProperty(ConfigProperties.DATA_PROVISIONING_PATH_CSV, Paths.get("data", "test-eng-climate-summary.csv").toString() );
	}
	
	/*
	private static final Path LOG4J_CONFIG_FILE = Path.of("src", "test", "resources", "log4j", "log4j2.xml");
	private static final Path LOG_DIR = Path.of("build", "log", "test");
	private static final String APP_NAME = "testAppName";
	
	private static final Logger log;
	
	static
	{
		if (System.getProperty("log4j2.configurationFile") == null) {
			System.setProperty("dir.log", LOG_DIR.toString());
			System.setProperty("app.name", APP_NAME);
			//System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager"); // Jersey
			//final String logPatternDateTimeFormat = tempResources
			//		.getString(Resources.Log.LAYOUT_PATTERN_DATETIMEFORMAT.getId());
			System.setProperty("log.local.layoutPattern.dateTimeFormat", "%d{ISO8601}{UTC}Z");
			final File log4jConfig = LOG4J_CONFIG_FILE.toFile();
			System.setProperty("log4j2.configurationFile", log4jConfig.getAbsolutePath());
			//System.setProperty(ConfigProperties.Log4j.CLOCK, AppClock.class.getCanonicalName());
		}
		
		log = LogManager.getLogger(BaseTest.class);
	}
	*/
}
