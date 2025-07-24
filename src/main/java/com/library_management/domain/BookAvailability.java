package com.library_management.domain;

public enum BookAvailability {
//	Defining all the status constants
	AVAILABLE("A"),
	ISSUED("I");
	
	private String displayName;
	
//	Constructor
	BookAvailability(String displayName){
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
