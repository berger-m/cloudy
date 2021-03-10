package com.cloudy.business.entities;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.cloudy.business.entities.attributes.Identifiable;
import com.cloudy.business.entities.attributes.OptimisticLock;

@Entity
@Table(name = "entries")
public class Entry implements Identifiable, OptimisticLock<Integer> {

	public static final Entry EMPTY_ENTRY = new Entry();

	@Id
	@Column(name = "entry_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Version
	private Integer version;

	@ManyToOne
	@JoinColumn(name = Station.DB_COLUMN_ID)
	private Station station;

	public static final String DB_COLUMN_DATE = "date";
	@Column(name = DB_COLUMN_DATE)
	private LocalDate date;

	public static final String DB_COLUMN_MEAN_TEMP = "meanTemp";
	@Column(name = DB_COLUMN_MEAN_TEMP)
	private Double meanTemp;

	public static final String DB_COLUMN_HMMT = "highestMonthlyMaxTemp";
	@Column(name = DB_COLUMN_HMMT)
	private Double highestMonthlyMaxTemp;

	public static final String DB_COLUMN_LMMT = "lowestMonthlyMinTemp";
	@Column(name = DB_COLUMN_LMMT)
	private Double lowestMonthlyMinTemp;

	public Entry() {
		id = -1;
		station = null;
		date = LocalDate.of(1970, 1, 1);
		meanTemp = null;
		highestMonthlyMaxTemp = null;
		lowestMonthlyMinTemp = null;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
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
		final String stationStr = Objects.nonNull(station) ? "Station=" + station.getId() + ", " : "null";

		return "( " + stationStr + DB_COLUMN_DATE + "=" + date + ", " + DB_COLUMN_MEAN_TEMP + "=" + meanTemp + ", "
				+ DB_COLUMN_HMMT + "=" + highestMonthlyMaxTemp + ", " + DB_COLUMN_LMMT + "=" + lowestMonthlyMinTemp
				+ " )";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(highestMonthlyMaxTemp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		temp = Double.doubleToLongBits(lowestMonthlyMinTemp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(meanTemp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((station == null) ? 0 : station.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entry other = (Entry) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(highestMonthlyMaxTemp) != Double.doubleToLongBits(other.highestMonthlyMaxTemp))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(lowestMonthlyMinTemp) != Double.doubleToLongBits(other.lowestMonthlyMinTemp))
			return false;
		if (Double.doubleToLongBits(meanTemp) != Double.doubleToLongBits(other.meanTemp))
			return false;
		if (station == null) {
			if (other.station != null)
				return false;
		} else if (!station.equals(other.station))
			return false;
		return true;
	}
}
