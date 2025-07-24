package com.library_management.dao;

import java.sql.PreparedStatement;

import com.library_management.domain.Book;

public interface BookDAOInterface {
	void addBook(Book book);
	
	void updateBookDetails(int id, Book book);
	
	void updateBookAvailability(int id, String availability);
	
	void deleteBook(int id);
}
