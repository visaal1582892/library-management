package com.library_management.services;

public interface BookServiceInterface {
	void validateAddBook(String title, String author, String category, char status, char availability);
	
	void updateBook(String title, String author, String category, char status, char availability);
}
