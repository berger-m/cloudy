package com.cloudy.web.beans.csv;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { ClimateRecord.CSV_PROP_STATION_NAME, ClimateRecord.CSV_PROP_PROVINCE,
		ClimateRecord.CSV_PROP_DATE, ClimateRecord.CSV_PROP_MEAN_TEMP, ClimateRecord.CSV_PROP_HMMT,
		ClimateRecord.CSV_PROP_LMMT })
public class ClimateRecord {

	public static final String CSV_PROP_STATION_NAME = "stationName";
	@XmlElement(name = CSV_PROP_STATION_NAME)
	private String stationName;

	public static final String CSV_PROP_PROVINCE = "province";
	@XmlElement(name = CSV_PROP_PROVINCE)
	private String province;

	public static final String CSV_PROP_DATE = "date";
	@XmlElement(name = CSV_PROP_DATE)
	private LocalDate date;

	public static final String CSV_PROP_MEAN_TEMP = "meanTemp";
	@XmlElement(name = CSV_PROP_MEAN_TEMP)
	private Double meanTemp;

	public static final String CSV_PROP_HMMT = "highestMonthlyMaxTemp";
	@XmlElement(name = CSV_PROP_HMMT)
	private Double highestMonthlyMaxTemp;

	public static final String CSV_PROP_LMMT = "lowestMonthlyMinTemp";
	@XmlElement(name = CSV_PROP_LMMT)
	private Double lowestMonthlyMinTemp;

	public ClimateRecord() {
		stationName = "";
		province = "";
		date = LocalDate.of(1970, 1, 1);
		meanTemp = null;
		highestMonthlyMaxTemp = null;
		lowestMonthlyMinTemp = null;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getMeanTemp() {
		return meanTemp;
	}

	public void setMeanTemp(Double meanTemp) {
		this.meanTemp = meanTemp;
	}

	public Double getHighestMonthlyMaxTemp() {
		return highestMonthlyMaxTemp;
	}

	public void setHighestMonthlyMaxTemp(Double highestMonthlyTemp) {
		this.highestMonthlyMaxTemp = highestMonthlyTemp;
	}

	public Double getLowestMonthlyMinTemp() {
		return lowestMonthlyMinTemp;
	}

	public void setLowestMonthlyMinTemp(Double lowestMonthlyTemp) {
		this.lowestMonthlyMinTemp = lowestMonthlyTemp;
	}

	public String toString() {
		return "( " + CSV_PROP_STATION_NAME + "=" + stationName + ", " + CSV_PROP_PROVINCE + "=" + province + ", "
				+ CSV_PROP_DATE + "=" + date + ", " + CSV_PROP_MEAN_TEMP + "=" + meanTemp + ", " + CSV_PROP_HMMT + "="
				+ highestMonthlyMaxTemp + ", " + CSV_PROP_LMMT + "=" + lowestMonthlyMinTemp + " )";
	}
}
