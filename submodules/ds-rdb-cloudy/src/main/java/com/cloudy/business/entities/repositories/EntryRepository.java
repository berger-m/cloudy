package com.cloudy.business.entities.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudy.business.entities.Entry;

/**
 * Persist {@link Entry} objects as rows in a relational database
 */
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

	/**
	 * A query that applies the date filtering
	 * 
	 * @param fromDate {@link LocalDate} start of date range
	 * @param toDate   {@link LocalDate} end of date range
	 * @return {@link List}&lt;{@link Entry}&gt; entries within the date range
	 */
	public List<Entry> findAllByDateBetween(final LocalDate fromDate, final LocalDate toDate);

	/**
	 * A query that finds all entries for specific station
	 * 
	 * @param stationId {@code long} uniquely identifies a {@link Station}
	 * @return {@link List}&lt;{@link Entry}&gt; entries for the requested
	 *         {@link Station}
	 */
	public List<Entry> findAllByStation_Id(final long stationId);
}
