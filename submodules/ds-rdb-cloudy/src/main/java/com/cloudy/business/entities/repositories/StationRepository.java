package com.cloudy.business.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudy.business.entities.Station;

/**
 * Persist {@link Station} objects as rows in a relational database
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

	/**
	 * This query should only find, at most, one result because a db unique
	 * constraint on StationName+Province pair.
	 * 
	 * @param paramStationName {@link String} name of the station
	 * @param paramProvince    {@link String} province
	 * @return {@link List}&lt;{@link Station}&gt; matching station
	 */
	public List<Station> findAllByNameAndProvince(final String paramStationName, final String paramProvince);
}
