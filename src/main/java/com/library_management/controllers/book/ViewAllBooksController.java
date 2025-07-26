package com.library_management.controllers.book;

import java.util.List;

import com.library_management.dao.implementation.BookDAOImplementation;
import com.library_management.domain.Book;
import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.exceptions.DatabaseException;
import com.library_management.services.implementation.BookServiceImplementation;
import com.library_management.utilities.CustomBookForTableView;
import com.library_management.utilities.ResponseHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

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
	private TableColumn<CustomBookForTableView, Void> actionsColumn;
	@FXML
	private Text message;

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
			List<Book> books = new BookServiceImplementation().validateViewAllBooks();
			for (Book book : books) {
				bookData.add(new CustomBookForTableView(book.getBookId(), book.getTitle(), book.getAuthor(),
						book.getCategory(), book.getStatus(), book.getAvailability()));
			}
		} catch (DatabaseException e) {
			ResponseHandler.showResponse(message, "Cannot Fetch Books Data...", Color.RED);
		}

		viewBooksTable.setItems(bookData);
		addActionsToTable();
		ResponseHandler.showResponse(message, "Books Fetched Succesfully...", Color.GREEN);
	}

	public void addActionsToTable() {
		actionsColumn.setCellFactory(
				new Callback<TableColumn<CustomBookForTableView, Void>, TableCell<CustomBookForTableView, Void>>() {

					@Override
					public TableCell<CustomBookForTableView, Void> call(
							TableColumn<CustomBookForTableView, Void> param) {

						return new TableCell<CustomBookForTableView, Void>(){
							ToggleButton toggleAvailability=new ToggleButton("Toggle Availability");
							Button deleteButton=new Button("Delete");
							
							{
//								Updating Event Handling
								toggleAvailability.setOnAction(event -> {
									CustomBookForTableView book=getTableView().getItems().get(getIndex());
									String newAvailabilityValue=book.getAvailability()=="Available"?"Issued":"Available";
									Book currBook=new Book(book.getBookId(),book.getTitle(),book.getAuthor(),BookCategory.getEnumConstant(book.getCategory()),BookStatus.getEnumConstant(book.getStatus().substring(0,1)),BookAvailability.getEnumConstant(book.getAvailability().substring(0,1)));
									
									try {
										new BookDAOImplementation().updateBookAvailability(currBook,newAvailabilityValue.substring(0,1));
										book.setAvailability(newAvailabilityValue);
										viewBooksTable.refresh();
										ResponseHandler.showResponse(message, "Succesfully Updated Availability...", Color.GREEN);
									}catch(DatabaseException e) {
										ResponseHandler.showResponse(message, e.getMessage(), Color.RED);
									}
								});
								
//								Deleting Event handling
								deleteButton.setOnAction(event -> {
									CustomBookForTableView book=getTableView().getItems().get(getIndex());
									Book currentBook=new Book(book.getBookId(),book.getTitle(),book.getAuthor(),BookCategory.getEnumConstant(book.getCategory()),BookStatus.getEnumConstant(book.getStatus().substring(0,1)),BookAvailability.getEnumConstant(book.getAvailability().substring(0,1)));
									getTableView().getItems().remove(getIndex());
									
									ResponseHandler.showResponse(message, "Book Deleted Succesfuly...", Color.GREEN);
									
									try {
										new BookDAOImplementation().deleteBook(currentBook);
									}catch(DatabaseException e) {
										ResponseHandler.showResponse(message, e.getMessage(), Color.RED);
									}
								});
							}
							
							
							@Override
		                    protected void updateItem(Void item, boolean empty) {
		                        super.updateItem(item, empty);
		                        if (empty) {
		                            setGraphic(null);
		                        } else {
		                            HBox buttons = new HBox(10, toggleAvailability, deleteButton);
		                            buttons.setPadding(new Insets(5));
		                            setGraphic(buttons);
		                        }
		                    }
						};
					}
				});
	}
}