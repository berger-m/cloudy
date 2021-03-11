package com.cloudy.web.controller;

import com.cloudy.business.entities.repositories.EntryRepository;

/**
 * Common elements to the ClimateControllers
 */
public abstract class AbstractClimateController {

	public static final String SEGMENT = "climate";

	private EntryRepository entryRepo;

	public AbstractClimateController(final EntryRepository paramEntryRepo) {
		setEntryRepo(paramEntryRepo);
	}

	public EntryRepository getEntryRepo() {
		return entryRepo;
	}

	public void setEntryRepo(EntryRepository entryRepo) {
		this.entryRepo = entryRepo;
	}
}