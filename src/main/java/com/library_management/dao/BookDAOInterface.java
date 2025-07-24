package com.library_management.dao;

import java.sql.PreparedStatement;

import com.library_management.domain.Book;
import com.library_management.exceptions.DatabaseException;

public interface BookDAOInterface {
	void addBook(Book book) throws DatabaseException;
	
	void updateBookDetails(int id, Book book);
	
	void updateBookAvailability(int id, String availability);
	
	void deleteBook(int id);
}
