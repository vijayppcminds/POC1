package com.assignment.util;

public enum MediaEnum {

	REQUESTBEAN("RequestBean"),
	FILTER_REGEX("^(censoring)$"),
	LEVEL_REGEX("^(Censored|Uncensored|-)$"),
	FILTERED_VALUES("filteredValues"),
	SEARCHABLE_ITEMS("searchableItems"),
	SEMI_COLON(";");
	
	private String param;
	
	MediaEnum(String param){
		this.param = param;
	}
	
	public String getParam(){
		return param;
	}
}
