package com.cloudy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudy.business.entities.Entry;
import com.cloudy.business.entities.Station;
import com.cloudy.business.entities.repositories.EntryRepository;

/**
 * Handles requests for the StationDetails page
 * (resources/templates/stationDetails.html) on URL Path climate/stationDetails.
 * 
 * This page provides the full details of each {@link Entry} associated with the
 * selected {@link Station}
 */
@Controller
@RequestMapping(AbstractClimateController.SEGMENT + "/" + ClimateStationDetailsController.SEGMENT)
public class ClimateStationDetailsController extends AbstractClimateController {

	public static final String SEGMENT = "stationDetails";

	@Autowired
	public ClimateStationDetailsController(final EntryRepository paramEntryRepo) {
		super(paramEntryRepo);
	}

	/**
	 * Searches the Cloudy db for all records associated with this {@link Station}
	 * 
	 * @param id    {@link String} uniquely identifies a {@link Station}
	 * @param model {@link Model}
	 * @return {@link String}
	 */
	@GetMapping("/{id}")
	public String stationDetails(@PathVariable final String id, final Model model) {
		long stationId = Long.parseLong(id);
		model.addAttribute("climateRecords", getEntryRepo().findAllByStation_Id(stationId));
		return "stationDetails";
	}

}
