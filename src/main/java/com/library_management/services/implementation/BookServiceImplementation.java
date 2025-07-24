package com.library_management.services.implementation;

import com.library_management.dao.implementation.BookDAOImplementation;
import com.library_management.domain.Book;
import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.BookServiceInterface;

public class BookServiceImplementation implements BookServiceInterface {
	public void validateAddBook(String title, String author, BookCategory category) throws InvalidDetailsException, DatabaseException {
		if(title.trim().equals("") || author.trim().equals("") || category==null) {
			throw new InvalidDetailsException("All Details Must Be Given...");
		}
		else if(title.length()>255 || author.length()>255) {
			throw new InvalidDetailsException("Lengths Of Fields Exceeded Max Length...");
		}
		else {
			Book newBook=new Book(title, author, category);
			try {
				new BookDAOImplementation().addBook(newBook);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				throw new DatabaseException(e.getMessage());
			}
		}
	}

	@Override
	public void validateViewAllBooks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateUpdateBook(int id, String title, String author, BookCategory category, BookStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateUpdateBookAvailability(int id, BookAvailability availability) {
		// TODO Auto-generated method stub
		
	}
}
