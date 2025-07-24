package com.library_management.services;

public interface BookServiceInterface {
	void validateAddBook(String title, String author, String category);
	
	void validateUpdateBook(String title, String author, String category, char status);
	
	void validateUpdateBookAvailability(char availability);
	
	void validateViewAllBooks();
}
