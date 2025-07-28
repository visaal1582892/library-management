package com.library_management.dao;

import java.util.List;

import com.library_management.domain.Book;
import com.library_management.exceptions.DatabaseException;

public interface BookDAOInterface {
	int addBook(Book book) throws DatabaseException;
	
	void updateBookDetails(Book oldBook, Book newBook) throws DatabaseException;
	
	List<Book> selectAllBooks() throws DatabaseException;
	
	Book selectBookById(int id) throws DatabaseException;
	
	List<Book> selectAllMemberBooks(int memberId) throws DatabaseException;

	void updateBookAvailability(Book book, String availability) throws DatabaseException;

	void deleteBook(Book oldBook) throws DatabaseException;
}
