package com.cloudy.business.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudy.business.entities.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

	/**
	 * There is a db unique constraint on StationName+Province
	 * 
	 * @param paramStationName
	 * @param paramProvince
	 * @return
	 */
	public List<Station> findAllByNameAndProvince(final String paramStationName, final String paramProvince);
}
