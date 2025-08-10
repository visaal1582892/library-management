package com.library_management.controllers;

import java.io.IOException;
import java.util.List;

import com.library_management.App;
import com.library_management.dao.implementation.ReportsDaoImplementation;
import com.library_management.domain.IssueRecord;
import com.library_management.utilities.CustomClassForListOfOverdueBooks;
import com.library_management.utilities.ResponseHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ListOfOverDueBooks {
	@FXML
	private TableView<CustomClassForListOfOverdueBooks> overDueBooksCountTable;

	@FXML
	private TableColumn<CustomClassForListOfOverdueBooks, String> booksColumn;

	@FXML
	private TableColumn<CustomClassForListOfOverdueBooks, String> membersColumn;

	@FXML
	private TableColumn<CustomClassForListOfOverdueBooks, String> membersNameColumn;

	@FXML
	private TableColumn<CustomClassForListOfOverdueBooks, String> issueDateColumn;

	@FXML
	private TableColumn<CustomClassForListOfOverdueBooks, String> titleColumn;

	@FXML
	private Text message;

	@FXML
	private void backButton() throws IOException {
		App.setRoot("reports");
	}

	@FXML
	private void homeButton() throws IOException {
		App.setRoot("home");
	}

//    Creating Observable list
	private ObservableList<CustomClassForListOfOverdueBooks> countData = FXCollections.observableArrayList();

	@FXML
	public void initialize() {

//		Creating cell value factories for AllPermission columns

		membersColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
		membersNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
		booksColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
		issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));

		try {
			List<List<String>> countMap = new ReportsDaoImplementation().getOverdueBooks();
			countMap.forEach(b -> countData
					.add(new CustomClassForListOfOverdueBooks(b.get(0), b.get(1), b.get(2), b.get(3), b.get(4))));
		} catch (Exception e) {
			ResponseHandler.showResponse(message, "Cannot Fetch Books Data...", Color.RED);
		}

		overDueBooksCountTable.setItems(countData);
		ResponseHandler.showResponse(message, "Report Data Fetched Succesfully...", null);
	}
}
