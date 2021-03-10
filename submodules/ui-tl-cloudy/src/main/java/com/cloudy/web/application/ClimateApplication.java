package com.cloudy.web.application;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cloudy.business.entities.Entry;
import com.cloudy.business.entities.Station;
import com.cloudy.business.entities.repositories.EntryRepository;
import com.cloudy.business.entities.repositories.StationRepository;
import com.cloudy.web.beans.csv.ClimateRecord;
import com.cloudy.web.io.csv.Utils;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@SpringBootApplication
@ComponentScan("com.cloudy.web")
@EntityScan("com.cloudy")
@EnableJpaRepositories("com.cloudy")
public class ClimateApplication {

	private static final Logger log = LogManager.getLogger(ClimateApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClimateApplication.class, args);
	}

	@Bean
	public ApplicationRunner initializer(final EntryRepository entryRepo, final StationRepository stationRepo) {
		return args -> {
			// Populate the database with a sample data set
			final CsvMapper csvMapper = Utils.getCsvMapper();
			List<ClimateRecord> lines = null;

			// Override the config property to load a smaller/different dataset for testing
			// purposes
			final String provisioningPath = System.getProperties().getProperty(
					ConfigProperties.DATA_PROVISIONING_PATH_CSV,
					Paths.get("data", "eng-climate-summary.csv").toString());
			final URL resourceLocation = ClimateRecord.class.getClassLoader().getResource(provisioningPath);
			log.atDebug().log("Populating with data from: [{}]", () -> resourceLocation.toString());
			try (final InputStream climateCsvData = resourceLocation.openStream()) {
				final CsvSchema schema = Utils.getClimateRecordFileSchema();
				final MappingIterator<ClimateRecord> it = csvMapper.readerFor(ClimateRecord.class).with(schema)
						.readValues(climateCsvData);
				lines = it.readAll();
			} catch (final Exception e) {
				log.atError().withThrowable(e).log("Failed to load ClimateRecords from: [{}]",
						() -> resourceLocation.toString());
			}

			for (final ClimateRecord line : lines) {
				final List<Station> dbStations = stationRepo.findAllByNameAndProvince(line.getStationName(),
						line.getProvince());
				Station station;
				if (dbStations.isEmpty()) {
					// Station not found in DB, add it
					station = Utils.convertClimateRecordToStation(line);
					station = stationRepo.save(station);
				} else {
					station = dbStations.iterator().next();
				}

				final Entry entry = Utils.convertClimateRecordToEntry(line);
				if (Entry.EMPTY_ENTRY.equals(entry)) {
					continue;
				}
				entry.setStation(station);
				entryRepo.save(entry);
			}

			System.out.println("Added " + stationRepo.findAll().size() + " Stations to testdb.");
			System.out.println("Added " + entryRepo.findAll().size() + " Entries to testdb.");
		};
	}
}
