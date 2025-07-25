package com.library_management.dao;

import java.sql.PreparedStatement;
import java.util.List;

import com.library_management.domain.Book;
import com.library_management.exceptions.DatabaseException;

public interface BookDAOInterface {
	void addBook(Book book) throws DatabaseException;
	
	void updateBookDetails(Book oldBook, Book newBook) throws DatabaseException;
	
	void updateBookAvailability(int id, String availability);
	
	void deleteBook(int id);
	
	List<Book> selectAllBooks() throws DatabaseException;
	
	Book selectBookById(int id) throws DatabaseException;
}
