package com.library_management.services.implementation;

import com.library_management.domain.Book;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.BookServiceInterface;

public class BookServiceImplementation implements BookServiceInterface {
	public void validateAddBook(String title, String author, String category) throws InvalidDetailsException {
		if(title.trim().equals("") || author.trim().equals("") || category.trim().equals("")) {
			throw new InvalidDetailsException("All Details Must Be Given...");
		}
		else if(title.length()>255 || author.length()>255 || category.length()>100) {
			throw new InvalidDetailsException("Lengths Of Fields Exceeded Max Length...");
		}
		else {
			Book newBook=new Book(title, author, category);
			
		}
	}

	@Override
	public void validateUpdateBook(String title, String author, String category, char status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateUpdateBookAvailability(char availability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateViewAllBooks() {
		// TODO Auto-generated method stub
		
	}
}
