package com.cloudy.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;

import com.cloudy.business.entities.attributes.Identifiable;
import com.cloudy.business.entities.attributes.OptimisticLock;

/**
 * A DB-entity representing a climate station<br>
 * Annotated with JPA to allow for easy migration between database
 * implementations.
 */
@Entity
@Table(name = "stations", uniqueConstraints = {
		@UniqueConstraint(columnNames = { Station.DB_COLUMN_STATION_NAME, Station.DB_COLUMN_PROVINCE }) })
public class Station implements Identifiable, OptimisticLock<Integer> {

	public static final String DB_COLUMN_ID = "station_id";
	@Id
	@Column(name = DB_COLUMN_ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Version
	private Integer version;

	public static final String DB_COLUMN_STATION_NAME = "name";
	/** Name of the climate station */
	@NotBlank(message = DB_COLUMN_STATION_NAME + " is mandatory")
	@Column(name = DB_COLUMN_STATION_NAME)
	private String name;

	public static final String DB_COLUMN_PROVINCE = "province";
	/** Province where the station is located */
	@NotBlank(message = DB_COLUMN_PROVINCE + " is mandatory")
	@Column(name = DB_COLUMN_PROVINCE)
	private String province;

	public Station() {
		id = -1;
		version = 0;
		name = "";
		province = "";
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Integer getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public void setName(String stationName) {
		this.name = stationName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String toString() {
		return "( " + DB_COLUMN_ID + "=" + id + ", " + DB_COLUMN_VERSION + "=" + version + ", " + DB_COLUMN_STATION_NAME
				+ "=" + name + ", " + DB_COLUMN_PROVINCE + "=" + province + " )";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Station other = (Station) obj;
		if (id != other.id)
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
