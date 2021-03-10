package com.cloudy.web;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import com.cloudy.web.beans.csv.ClimateRecord;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvFactory;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@AutoConfigureMockMvc
public class ServingWebContentApplicationTests extends BaseClimateSpringBootTest {

	//private DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory ();
	
	@Autowired
	private MockMvc mvc;

	/*
	private static CsvMapper getCsvMapper() {
		final SimpleModule csvDateModule = new SimpleModule ();
		csvDateModule.addDeserializer(LocalDate.class, new LocalDateDeserializer( DateTimeFormatter.ofPattern("dd/MM/yyyy") ));
		csvDateModule.addSerializer(LocalDate.class, new LocalDateSerializer( DateTimeFormatter.ofPattern("dd/MM/yyyy") ));
		
		// Copied from com.jrest.api.rest.sampleHR.service.JacksonXmlProvider
		final CsvMapper csvMapper = CsvMapper.builder() //
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) //
				.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
				//.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE)  // TODO remove
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //
				.addModule(new JavaTimeModule()) //
				.addModule(csvDateModule) //
				.build();
		
		//csvMapper.registerModule(csvDateModule);
		return csvMapper;
		//final JrestXmlSerializerProvider modifiedXmlSerializer = new JrestXmlSerializerProvider(
		//		new XmlRootNameLookup());
		//final XmlMapper xmlMapperMod = (XmlMapper) xmlMapper.setSerializerProvider(modifiedXmlSerializer);
		//return xmlMapperMod;
	}
	
	@Test
	public void getLinesFromRawDataFile() throws Exception {
		final CsvMapper csvMapper = getCsvMapper();		
		// Schema for this raw data file
		CsvSchema schema = CsvSchema.builder()
		        .addColumn(ClimateRecord.CSV_PROP_STATION_NAME) // Station_Name
		        .addColumn(ClimateRecord.CSV_PROP_PROVINCE) // Province
		        .addColumn(ClimateRecord.CSV_PROP_DATE) // Date		        
		        .addColumn(ClimateRecord.CSV_PROP_MEAN_TEMP) // Mean_Temp
		        .addColumn(ClimateRecord.CSV_PROP_HMMT) // Highest_Monthly_Maxi_Temp
		        .addColumn(ClimateRecord.CSV_PROP_LMMT) // Lowest_Monthly_Min_Temp
		        .build().withHeader();
		
		List<ClimateRecord> lines = null;
		//try ( final InputStream climateCsvData = Line.class.getClassLoader().getResourceAsStream(Path.of("resources", "data", "eng-climate-summary.csv").toString()) ) {
		final URL resourceLocation = ClimateRecord.class.getClassLoader().getResource(Paths.get("data", "eng-climate-summary.csv").toString());	
		System.out.println("Data from: [" + resourceLocation.toString() + "]");
		try ( final InputStream climateCsvData = resourceLocation.openStream() ) {
			//lines = reader.readValue(climateCsvData);
			//lines = reader.readValue(jsonF.createParser(climateCsvData));
			//System.out.println ("" + csvMapper.readerFor(Line.class).with(bootstrapSchema).readValue(climateCsvData) );
			
			// TODO consider checking the first line for "header" values, and skipping it			
			final MappingIterator<ClimateRecord> it = csvMapper.readerFor(ClimateRecord.class).with(schema).readValues(climateCsvData);
			lines = it.readAll();
			//lines
			
		} catch (final Exception e ) {
			System.out.println( "Exception: " + e.toString() );
		}
		
		assertNotNull(lines);
		assertFalse(lines.isEmpty());
		
		System.out.println("Lines: " + lines);
		
	}
	*/
	
	@Test
	public void getHello() {
		//ResultActions ra = mvc.perform(MockMvcRequestBuilders.get("/greeting?name=MyName"));
		//MvcResult result = ra.andReturn();
		
		//System.out.println("RA: [" + result.getResponse().getContentAsString() + "]");
		
		assertDoesNotThrow ( () ->  mvc.perform(MockMvcRequestBuilders.get("/climate/summary"))
			.andExpect(status().isOk()) );
		// https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-server-htmlunit-webdriver
		;
		
		//greeting
		
		
		//mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
		//		.andExpect(status().isOk())
		//		.andExpect(content().string(equalTo("Greetings from Spring Boot!")));
	}
}