package com.library_management.controllers.book;

import java.io.IOException;
import java.util.List;

import com.library_management.App;
import com.library_management.domain.Book;
import com.library_management.exceptions.DatabaseException;
import com.library_management.services.implementation.BookServiceImplementation;
import com.library_management.utilities.CustomBookForTableView;
import com.library_management.utilities.ResponseHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ViewAllBooksController {
	
//	Declaring all fields
	@FXML
    private TableView<CustomBookForTableView> viewBooksTable;
    @FXML
    private TableColumn<CustomBookForTableView, Integer> bookIdColumn;
    @FXML
    private TableColumn<CustomBookForTableView, String> titleColumn;
    @FXML
    private TableColumn<CustomBookForTableView, String> authorColumn;
    @FXML
    private TableColumn<CustomBookForTableView, String> categoryColumn;
    @FXML
    private TableColumn<CustomBookForTableView, String> statusColumn;
    @FXML
    private TableColumn<CustomBookForTableView, String> availabilityColumn;
    @FXML
    private Text message;
    
    @FXML
    private void backButton() throws IOException {
        App.setRoot("bookOptions");
    }

    @FXML
    private void homeButton() throws IOException {
        App.setRoot("home");
    }

    
//    Creating Observable list
    private ObservableList<CustomBookForTableView> bookData = FXCollections.observableArrayList();
	
	@FXML
    public void initialize() {
		
//		Creating cell value factories for AllPermission columns
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        try {
			List<Book> books=new BookServiceImplementation().validateViewAllBooks();
			for(Book book:books) {
				bookData.add(new CustomBookForTableView(book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getStatus(), book.getAvailability()));
			}
			viewBooksTable.setItems(bookData);
			ResponseHandler.showResponse(message, "Books Fetched Succesfully...", Color.GREEN);
			
		} catch (DatabaseException e) {
			ResponseHandler.showResponse(message, "Cannot Fetch Books Data...", Color.RED);
		}
		
        
    }
}