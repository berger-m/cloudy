package com.cloudy.web.data;

import java.time.LocalDate;

/**
 * Describes the curated test data set in file
 * /ui-tl-cloudy/src/test/resources/data/test-eng-climate-summary.csv
 *
 */
public class TestData {

	/** Lines of data (not including the header) in the test data set */
	public static final int TOTAL_ROWS = 35;

	/** None of the entries in the test data set are older than this date */
	public static final LocalDate MIN_DATE = LocalDate.of(2017, 8, 9);

	/** None of the entries in the test data set are any newer than this date */
	public static final LocalDate MAX_DATE = LocalDate.of(2018, 7, 6);

	public static final String NAME_OF_STATION_NORTH_COWICHAN_BC = "NORTH COWICHAN";
	public static final LocalDate DATE_OF_NORTH_COWICHAN_BC = LocalDate.of(2018, 01, 11);
	public static final int NUM_OF_NORTH_COWICHAN_BC_ENTRIES = 2;
}
