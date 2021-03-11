package com.cloudy.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudy.business.entities.Entry;
import com.cloudy.business.entities.repositories.EntryRepository;
import com.cloudy.web.ui.entities.FilterParams;

/**
 * Handles requests for the StationDetails page
 * (resources/templates/summary.html) on URL Path climate/summary.<br>
 * This page provides a summarized version of all the {@link Entry} in the
 * Cloudy db.
 */
@Controller
@RequestMapping(AbstractClimateController.SEGMENT + "/" + ClimateSummaryController.SEGMENT)
public class ClimateSummaryController extends AbstractClimateController {
	public static final String SEGMENT = "summary";

	private static final String ATTR_CLIMATE_RECORDS = "climateRecords";
	private static final String ATTR_FILTER_PARAMS = "filterDateRange";

	private static final DateTimeFormatter dateConverter = DateTimeFormatter.ISO_LOCAL_DATE;

	private static final String DEFAULT_FROM_DATE = "0001-01-01";
	private static final String DEFAULT_TO_DATE = "9999-01-01";

	private FilterParams filter;

	@Autowired
	public ClimateSummaryController(final EntryRepository paramEntryRepo) {
		super(paramEntryRepo);
		filter = new FilterParams();
		filter.getFilterParams().put(FilterParams.KEY_FROM_DATE, DEFAULT_FROM_DATE);
		filter.getFilterParams().put(FilterParams.KEY_TO_DATE, DEFAULT_TO_DATE);
	}

	@GetMapping
	public String showRecordSummaries(final Model model) {
		model.addAttribute(ATTR_CLIMATE_RECORDS, getEntryRepo().findAll());
		model.addAttribute(ATTR_FILTER_PARAMS, filter);
		return "summary";
	}

	@PostMapping
	public String updateFilterParameters(@ModelAttribute final FilterParams paramFilter, final Model model) {
		// TODO validate results that from/to are valid dates and that "from" is BEFORE
		// *to*
		String newFromDate = paramFilter.getFilterParams().get(FilterParams.KEY_FROM_DATE);
		String newToDate = paramFilter.getFilterParams().get(FilterParams.KEY_TO_DATE);

		if (Objects.isNull(newFromDate) || newFromDate.isEmpty()) {
			newFromDate = DEFAULT_FROM_DATE;
		}

		if (Objects.isNull(newToDate) || newToDate.isEmpty()) {
			newToDate = DEFAULT_TO_DATE;
		}

		filter.getFilterParams().put(FilterParams.KEY_FROM_DATE, newFromDate);
		filter.getFilterParams().put(FilterParams.KEY_TO_DATE, newToDate);

		model.addAttribute(ATTR_CLIMATE_RECORDS, applyFilter());
		model.addAttribute(ATTR_FILTER_PARAMS, filter);
		return "summary";
	}

	private List<Entry> applyFilter() {
		// TODO add paging to the query incase there are many millions of Entries in the
		// Cloudy database.
		final LocalDate fromDate = LocalDate
				.from(dateConverter.parse(filter.getFilterParams().get(FilterParams.KEY_FROM_DATE)));
		final LocalDate toDate = LocalDate
				.from(dateConverter.parse(filter.getFilterParams().get(FilterParams.KEY_TO_DATE)));
		return getEntryRepo().findAllByDateBetween(fromDate, toDate);
	}
}