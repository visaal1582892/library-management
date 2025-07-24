package com.library_management.domain;

public enum BookCategory {
//	Defining all the category constants
	FICTION("Fiction"),
	NON_FICTION("Non Fiction"),
	MYSTERY("Mystery"),
	ROMANCE("Romance"),
	SCIENCE_FICTION("Science Fiction"),
	FANTASY("Fantasy"),
	HORROR("Horror");
	
	private String displayName;
	
//	Constructor
	BookCategory(String displayName) {
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
