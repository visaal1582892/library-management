package com.library_management.domain;

public enum BookStatus {
//	Defining all the status constants
	ACTIVE("A"),
	INACTIVE("I");
	
	private String displayName;
	
//	Constructor
	BookStatus(String displayName){
		this.displayName=displayName;
	}
	
//	Defining all the related functions
	public String getStringValue() {
		return this.displayName;
	}
	
	@Override
	public String toString() {
		return this.displayName;
	}
}
