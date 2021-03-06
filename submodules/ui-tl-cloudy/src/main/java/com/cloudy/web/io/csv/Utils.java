package com.cloudy.web.io.csv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.cloudy.business.entities.Entry;
import com.cloudy.business.entities.Station;
import com.cloudy.web.beans.csv.ClimateRecord;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class Utils {

	/**
	 * Provides a Jackson CsvMapper that has been configured date serializers and
	 * deserializers that match the format of the CSV file
	 * 
	 * @return {@link CsvMapper} mapper
	 */
	public static CsvMapper getCsvMapper() {
		final SimpleModule csvDateModule = new SimpleModule();
		csvDateModule.addDeserializer(LocalDate.class,
				new LocalDateDeserializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		csvDateModule.addSerializer(LocalDate.class,
				new LocalDateSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		final CsvMapper csvMapper = CsvMapper.builder() //
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) //
				.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //
				.addModule(new JavaTimeModule()) //
				.addModule(csvDateModule) //
				.build();
		return csvMapper;
	}

	/**
	 * Provides the schema that matches the provided CSV file and ignores the header
	 * line
	 * 
	 * @return {@link CsvSchema} schema
	 */
	public static CsvSchema getClimateRecordFileSchema() {
		final CsvSchema schema = CsvSchema.builder() //
				.addColumn(ClimateRecord.CSV_PROP_STATION_NAME) // Station_Name
				.addColumn(ClimateRecord.CSV_PROP_PROVINCE) // Province
				.addColumn(ClimateRecord.CSV_PROP_DATE) // Date
				.addColumn(ClimateRecord.CSV_PROP_MEAN_TEMP) // Mean_Temp
				.addColumn(ClimateRecord.CSV_PROP_HMMT) // Highest_Monthly_Maxi_Temp
				.addColumn(ClimateRecord.CSV_PROP_LMMT) // Lowest_Monthly_Min_Temp
				.build().withHeader();
		return schema;
	}

	/**
	 * A convenience utility for translating a {@link ClimateRecord} bean into a
	 * {@link Station} DB-entity.
	 * 
	 * @param line {@link ClimateRecord} source bean
	 * @return {@link Station} DB-entity counterpart
	 */
	public static Station convertClimateRecordToStation(final ClimateRecord line) {
		final Station newStation = new Station();
		newStation.setName(line.getStationName());
		newStation.setProvince(line.getProvince());
		return newStation;
	}

	/**
	 * A convenience utility for translating a {@link ClimateRecord} bean into a
	 * {@link Entry} DB-entity.
	 * 
	 * @param line {@link ClimateRecord} source bean
	 * @return {@link Entry} DB-entity counterpart
	 */
	public static Entry convertClimateRecordToEntry(final ClimateRecord line) {
		final Entry entry = new Entry();
		entry.setDate(line.getDate());
		entry.setMeanTemp(line.getMeanTemp());
		entry.setHighestMonthlyMaxTemp(line.getHighestMonthlyMaxTemp());
		entry.setLowestMonthlyMinTemp(line.getLowestMonthlyMinTemp());
		return entry;
	}
}
