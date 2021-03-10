package com.cloudy.web.controller;

import com.cloudy.business.entities.repositories.EntryRepository;

public class ClimateController {

	public static final String SEGMENT = "/climate";

	private EntryRepository entryRepo;

	public ClimateController(final EntryRepository paramEntryRepo) {
		setEntryRepo(paramEntryRepo);
	}

	public EntryRepository getEntryRepo() {
		return entryRepo;
	}

	public void setEntryRepo(EntryRepository entryRepo) {
		this.entryRepo = entryRepo;
	}
}