package com.library_management.controllers.book;

import java.util.List;

import com.library_management.dao.implementation.BookDAOImplementation;
import com.library_management.domain.Book;
import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.implementation.BookServiceImplementation;
import com.library_management.utilities.ResponseHandler;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UpdateBookDetailsController {
	
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
	private TextField titleField;
	
	@FXML
	private TextField authorField;
	
	@FXML
	private ComboBox<BookCategory> categoryComboBox;
	
	@FXML
	private ComboBox<BookStatus> statusComboBox;
	
	@FXML
	private Text message;
	
	@FXML
    public void initialize() {
		try {
			List<Book> booksList=new BookDAOImplementation().selectAllBooks();
			this.setBooksList(booksList);
			for(Book book:booksList) {
				bookSelector.getItems().add(book.getBookId()+". "+book.getTitle());
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		
		categoryComboBox.getItems().addAll(BookCategory.values());
    	statusComboBox.getItems().addAll(BookStatus.values());
    }
	
	@FXML
	public void loadBookDetails() {
		int id=Integer.parseInt(bookSelector.getValue().split("\\.")[0].trim());
		Book selectedBook=this.getBooksList().stream()
				.filter(b -> b.getBookId()==id)
				.findFirst()
				.orElse(null);
		if(selectedBook!=null) {
			titleField.setText(selectedBook.getTitle());
			authorField.setText(selectedBook.getAuthor());
			categoryComboBox.setValue(selectedBook.getCategory());
			statusComboBox.setValue(selectedBook.getStatus());
		}
	}
	
	@FXML
	public void handleButtonClick() {
		try {
			if(bookSelector.getValue()==null) {
				throw new InvalidDetailsException("First Select Book To Update...");
			}
			int id=Integer.parseInt(bookSelector.getValue().split("\\.")[0].trim());
			String title=titleField.getText();
			String author=authorField.getText();
			BookCategory category=categoryComboBox.getSelectionModel().getSelectedItem();
			BookStatus status=statusComboBox.getSelectionModel().getSelectedItem();
			new BookServiceImplementation().validateUpdateBookDetails(id, title, author, category, status);
			ResponseHandler.showResponse(message, "Book Details Updated Succesfully...", Color.GREEN);
		}catch(InvalidDetailsException|DatabaseException e) {
			ResponseHandler.showResponse(message, e.getMessage(), Color.RED);
		}
		System.out.println(titleField.getText()+" "+
		authorField.getText()+" "+
		categoryComboBox.getSelectionModel().getSelectedItem()+" "+
		statusComboBox.getSelectionModel().getSelectedItem());
	}
}
