package com.library_management.controllers.book;

import com.library_management.domain.BookCategory;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddBookController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField availabilityField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    public void initialize() {
        // Example categories
    	for(BookCategory enumValue:BookCategory.values()) {
    		categoryComboBox.getItems().addAll(enumValue.getStringValue());
    	}
    }

   
    // Add methods to save book data, validate fields, etc.
}

