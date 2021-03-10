package com.cloudy.web;

import java.nio.file.Paths;

import org.springframework.boot.test.context.SpringBootTest;
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
}
