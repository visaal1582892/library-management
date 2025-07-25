package com.library_management.controllers.issue_and_return_books;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.library_management.dao.implementation.BookDAOImplementation;
import com.library_management.domain.Book;
import com.library_management.domain.IssueRecord;
import com.library_management.domain.IssueRecordStatus;
import com.library_management.exceptions.DatabaseException;
import com.library_management.services.IssueRecordServiceInterface;
import com.library_management.services.implementation.IssueRecordServiceImplementation;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class IssueBookController {

	IssueRecordServiceInterface service = new IssueRecordServiceImplementation();

	private List<Book> booksList;

	public void setBooksList(List<Book> booksList) {
		this.booksList = booksList;
	}

	public List<Book> getBooksList() {
		return this.booksList;
	}

	@FXML
	private ComboBox<String> bookSelector;

	@FXML
	private TextField memberIdField;

	@FXML
	public void initialize() {
		try {
			List<Book> booksList = new BookDAOImplementation().selectAllBooks();
			this.setBooksList(booksList);
			for (Book book : booksList) {
				bookSelector.getItems().add(book.getBookId() + ". " + book.getTitle());
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void loadBookDetails() {
		System.out.println("Book selected: " + bookSelector.getValue());
	}

	@FXML
	private void switchToIssueBookSubmit() throws IOException {
		int memberId = Integer.parseInt(memberIdField.getText().trim());
		int bookId = Integer.parseInt(bookSelector.getValue().split("\\.")[0].trim());
		IssueRecord issue = new IssueRecord(memberId, bookId, IssueRecordStatus.I, LocalDate.now());
		service.issueBook(issue);
	}
}
