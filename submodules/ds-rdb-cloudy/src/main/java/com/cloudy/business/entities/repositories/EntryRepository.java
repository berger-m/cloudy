package com.cloudy.business.entities.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudy.business.entities.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
	public List<Entry> findAllByDateBetween(final LocalDate fromDate, final LocalDate toDate);

	public List<Entry> findAllByStation_Id(final long stationId);
}
