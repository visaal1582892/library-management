package com.library_management.controllers.issue_and_return_books;

import java.io.IOException;
import java.util.List;

import com.library_management.domain.Book;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ReturnBookController {
	
	
	private List<Book> booksList;
	
	public void setBooksList(List<Book> booksList) {
		this.booksList=booksList;
	}
	
	public List<Book> getBooksList(){
		return this.booksList;
	}
	
	@FXML
	private ComboBox<String> bookSelector;
	
	@FXML
	private TextField memberIdField;

//	@FXML
//	public void initialize() {
//		try {
//			int memberId = Integer.parseInt(memberIdField.getText().trim());
//			Book book = new BookDAOImplementation().selectBookById(memberId);
////			this.setBooksList(booksList);
////			for (Book book : booksList) {
//				bookSelector.getItems().add(book.getBookId() + ". " + book.getTitle());
////			}
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//		}
//	}
	
	@FXML
	public void loadBookDetails() {
		System.out.println("Book selected: " + bookSelector.getValue());
	}
	
	@FXML
    private void switchToReturnBookSubmit() throws IOException {
		int memberId = Integer.parseInt(memberIdField.getText().trim());
		int bookId = Integer.parseInt(bookSelector.getValue().split("\\.")[0].trim());
//		service.returnBook(memberId, bookId);
    }
}

