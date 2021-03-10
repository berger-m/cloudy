package com.cloudy.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudy.business.entities.repositories.EntryRepository;

@Controller
@RequestMapping(ClimateController.SEGMENT + "/" + ClimateDetailsController.SEGMENT)
public class ClimateDetailsController extends ClimateController {
	
	public static final String SEGMENT = "stationDetails";
	
	@Autowired
	public ClimateDetailsController ( 
			final EntryRepository paramEntryRepo ) {
		super(paramEntryRepo);
	}

	@GetMapping("/{id}")
	public String stationDetails(@PathVariable final String id, final Model model) {
		long stationId = Long.parseLong(id);
		model.addAttribute("climateRecords", getEntryRepo().findAllByStation_Id(stationId));
		return "stationDetails";
	}
	
}