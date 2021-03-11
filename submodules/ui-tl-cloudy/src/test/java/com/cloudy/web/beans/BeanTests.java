package com.cloudy.web.beans;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.cloudy.business.entities.Entry;
import com.cloudy.business.entities.Station;
import com.cloudy.web.beans.csv.ClimateRecord;

public class BeanTests {

	@Test
	void csvBeanClimateRecordToStringTest() {
		final String stationName = "someStation";
		final String province = "BC";
		final LocalDate date = LocalDate.of(2020, 3, 30);
		final double meanTemp = -12.5;
		final double hmmt = 8.2;
		final double lmmt = -25.4;
		final String expectedBeanString = "( " + ClimateRecord.CSV_PROP_STATION_NAME + "=" + stationName + ", "
				+ ClimateRecord.CSV_PROP_PROVINCE + "=" + province + ", " + ClimateRecord.CSV_PROP_DATE + "=" + date
				+ ", " + ClimateRecord.CSV_PROP_MEAN_TEMP + "=" + meanTemp + ", " + ClimateRecord.CSV_PROP_HMMT + "="
				+ hmmt + ", " + ClimateRecord.CSV_PROP_LMMT + "=" + lmmt + " )";

		final ClimateRecord bean = new ClimateRecord();
		bean.setStationName(stationName);
		bean.setProvince(province);
		bean.setDate(date);
		bean.setMeanTemp(meanTemp);
		bean.setHighestMonthlyMaxTemp(hmmt);
		bean.setLowestMonthlyMinTemp(lmmt);

		final String actualBeanString = bean.toString();
		assertEquals(expectedBeanString, actualBeanString);
	}

	@Test
	void dbBeanStationToStringTest() {
		final String stationName = "someStation";
		final String province = "BC";
		final long id = -1;
		final int version = 0;
		final String expectedBeanString = "( " + Station.DB_COLUMN_ID + "=" + id + ", " + Station.DB_COLUMN_VERSION
				+ "=" + version + ", " + Station.DB_COLUMN_STATION_NAME + "=" + stationName + ", "
				+ Station.DB_COLUMN_PROVINCE + "=" + province + " )";
		final Station bean = new Station();
		bean.setName(stationName);
		bean.setProvince(province);

		final String actualBeanString = bean.toString();
		assertEquals(expectedBeanString, actualBeanString);
	}

	@Test
	void dbBeanEntryToStringTest() {
		final Station station = new Station();
		final LocalDate date = LocalDate.of(2020, 3, 30);
		final double meanTemp = -12.5;
		final double hmmt = 8.2;
		final double lmmt = -25.4;
		final String expectedBeanString = "( " + "Station=" + station.getId() + ", " + Entry.DB_COLUMN_DATE + "=" + date
				+ ", " + Entry.DB_COLUMN_MEAN_TEMP + "=" + meanTemp + ", " + Entry.DB_COLUMN_HMMT + "=" + hmmt + ", "
				+ Entry.DB_COLUMN_LMMT + "=" + lmmt + " )";

		final Entry bean = new Entry();
		bean.setStation(station);
		bean.setDate(date);
		bean.setMeanTemp(meanTemp);
		bean.setHighestMonthlyMaxTemp(hmmt);
		bean.setLowestMonthlyMinTemp(lmmt);

		final String actualBeanString = bean.toString();
		assertEquals(expectedBeanString, actualBeanString);
	}

}
