package com.library_management.domain;

public enum BookAvailability {
//	Defining all the constants
	AVAILABLE,ISSUED;
	
//	Defining all the functions
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
}
