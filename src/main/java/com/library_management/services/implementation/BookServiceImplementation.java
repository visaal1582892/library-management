package com.library_management.services.implementation;

import java.util.List;

import com.library_management.constants.BookCategory;
import com.library_management.constants.BookStatus;
import com.library_management.dao.implementation.BookDaoImplementation;
import com.library_management.domain.Book;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.BookServiceInterface;

public class BookServiceImplementation implements BookServiceInterface {

	@Override
	public void validateAddBook(String title, String author, BookCategory category)
			throws InvalidDetailsException, DatabaseException {
		if (title.trim().equals("")) {
			throw new InvalidDetailsException("Title Must Not Be Empty...");
		}
		if (author.trim().equals("")) {
			throw new InvalidDetailsException("Author Must Not Be Empty...");
		}
		if (category == null) {
			throw new InvalidDetailsException("Must Select A Valid Category...");
		}
		if (author.length() > 60 || author.length() < 3) {
			throw new InvalidDetailsException("Length Of Author Must Be greater Than 3 And Less Than 60...");
		}
		if(title.length() > 60 || title.length() < 3) {
			throw new InvalidDetailsException("Length Of Title Must Be greater Than 3 And Less Than 60...");
		}
		
		Book newBook = new Book(title, author, category);
		try {
			new BookDaoImplementation().addBook(newBook);
		} catch (DatabaseException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public List<Book> validateViewAllBooks() throws DatabaseException {
		try {
			List<Book> bookList = new BookDaoImplementation().selectAllBooks();
			return bookList;
		} catch (DatabaseException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	@Override
	public void validateUpdateBookDetails(int id, String title, String author, BookCategory category, BookStatus status)
			throws InvalidDetailsException, DatabaseException {
		if (title.trim().equals("")) {
			throw new InvalidDetailsException("Title Must Not Be Empty...");
		}
		if (author.trim().equals("")) {
			throw new InvalidDetailsException("Author Must Not Be Empty...");
		}
		if (category == null) {
			throw new InvalidDetailsException("Select A Valid Category...");
		}
		if (status == null) {
			throw new InvalidDetailsException("Select A Valid Status...");
		}
		if (author.length() > 60 || author.length() < 3) {
			throw new InvalidDetailsException("Length Of Author Must Be greater Than 3 And Less Than 60...");
		}
		if(title.length() > 60 || title.length() < 3) {
			throw new InvalidDetailsException("Length Of Title Must Be greater Than 3 And Less Than 60...");
		}
		
		Book currentBook = null;
		try {
			currentBook = new BookDaoImplementation().selectBookById(id);
		} catch (DatabaseException e) {
			throw new DatabaseException("Mentioned Book Not Found...");
		}
		if (currentBook == null) {
			throw new InvalidDetailsException("Book Details Not Found...");
		}

//		Checking if atleast one detail is changed or not
		if (currentBook.getTitle().equals(title) && currentBook.getAuthor().equals(author)
				&& currentBook.getCategory() == category && currentBook.getStatus() == status) {
			throw new InvalidDetailsException("Atleast One Detail Should Be Updated...");
		}

//		Creating a new Book obj
		Book newBook = new Book(id, title, author, category, status);
		new BookDaoImplementation().updateBookDetails(currentBook, newBook);

	}
}
