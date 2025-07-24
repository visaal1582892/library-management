package com.library_management.services.implementation;

import com.library_management.exceptions.InvalidDetailsException;

public class BookServiceImplementation {
	void validateAddBook(String title, String author, String category) throws InvalidDetailsException {
		if(title.trim().equals("") || author.trim().equals("") || category.trim().equals("")) {
			throw new InvalidDetailsException("All Details Must Be Given...");
		}
		else if(title.length()>255 || author.length()>255 || category.length()>100) {
			throw new InvalidDetailsException("Lengths Of Fields Exceeded Max Length...");
		}
		else {
			
		}
	}
}
