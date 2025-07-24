package com.library_management.services.implementation;

import com.library_management.exceptions.InvalidDetailsException;

public class BookServiceImplementation {
	void validateAddBook(String title, String author, String category) throws InvalidDetailsException {
		if(title.trim().equals("") || author.trim().equals("") || category.trim().equals("")) {
			throw new InvalidDetailsException("All Details Must Be Given...");
		}
//		else if()
	}
}
