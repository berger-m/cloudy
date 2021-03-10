package com.cloudy.web.ui.entities;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterParams {
	
	public static final String KEY_FROM_DATE = "fromDate";
	public static final String KEY_TO_DATE = "toDate";
	

	private Map<String,String> filterParams = new LinkedHashMap<> ();

	public Map<String,String> getFilterParams() {
		return filterParams;
	}

	public void setFilterParams(Map<String,String> filterParams) {
		this.filterParams = filterParams;
	}
}
