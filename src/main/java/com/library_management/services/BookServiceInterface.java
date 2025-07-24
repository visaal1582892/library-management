package com.library_management.services;

import com.library_management.exceptions.InvalidDetailsException;

public interface BookServiceInterface {
	void validateAddBook (String title, String author, String category) throws InvalidDetailsException;
	
	void validateUpdateBook(String title, String author, String category, char status);
	
	void validateUpdateBookAvailability(char availability);
	
	void validateViewAllBooks();
}
