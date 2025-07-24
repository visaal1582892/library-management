package com.library_management.domain;

public enum BookStatus {
//	Defining all the status constants
	ACTIVE,INACTIVE;
	
//	Defining all the related functions
	public char getCharValue() {
		int ordinal=this.ordinal();
		switch(ordinal) {
		case 0:
			return 'A';
		case 1:
			return 'I';
		default:
			return 'N';
		}
	}
	
	public String getStringValue() {
		int ordinal=this.ordinal();
		switch(ordinal) {
		case 0:
			return "Active";
		case 1:
			return "Inactive";
		default:
			return "NA";
		}
	}
}
