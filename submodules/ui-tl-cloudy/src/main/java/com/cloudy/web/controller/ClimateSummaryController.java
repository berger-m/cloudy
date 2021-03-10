package com.cloudy.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudy.business.entities.repositories.EntryRepository;
import com.cloudy.web.ui.entities.FilterParams;

@Controller
@RequestMapping(ClimateController.SEGMENT + "/" + ClimateSummaryController.SEGMENT)
public class ClimateSummaryController extends ClimateController {
	public static final String SEGMENT = "summary";

	private static final DateTimeFormatter dateConverter = DateTimeFormatter.ISO_LOCAL_DATE;

	private static final String DEFAULT_FROM_DATE = "0001-01-01";
	private static final String DEFAULT_TO_DATE = "9999-01-01";

	private FilterParams filter = new FilterParams();

	@Autowired
	public ClimateSummaryController(final EntryRepository paramEntryRepo) {
		super(paramEntryRepo);
		filter.getFilterParams().put(FilterParams.KEY_FROM_DATE, DEFAULT_FROM_DATE);
		filter.getFilterParams().put(FilterParams.KEY_TO_DATE, DEFAULT_TO_DATE);
	}

	@GetMapping
	public String showAll(final Model model) {
		model.addAttribute("climateRecords", getEntryRepo().findAll());
		model.addAttribute("filterDateRange", filter);
		return "summary";
	}

	@PostMapping
	public String filterResults(@ModelAttribute final FilterParams paramFilter, final Model model) {
		// TODO validate results that from/to are valid dates and that "from" is BEFORE
		// *to*
		filter.getFilterParams().put(FilterParams.KEY_FROM_DATE,
				paramFilter.getFilterParams().get(FilterParams.KEY_FROM_DATE));
		filter.getFilterParams().put(FilterParams.KEY_TO_DATE,
				paramFilter.getFilterParams().get(FilterParams.KEY_TO_DATE));

		final LocalDate fromDate = LocalDate
				.from(dateConverter.parse(filter.getFilterParams().get(FilterParams.KEY_FROM_DATE)));
		final LocalDate toDate = LocalDate
				.from(dateConverter.parse(filter.getFilterParams().get(FilterParams.KEY_TO_DATE)));
		model.addAttribute("climateRecords", getEntryRepo().findAllByDateBetween(fromDate, toDate));
		model.addAttribute("filterDateRange", paramFilter);
		return "summary";
	}
}