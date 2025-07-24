package com.library_management.controllers.book;

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
        categoryComboBox.getItems().addAll("Science", "History", "Fiction", "Biography");
    }

    // Add methods to save book data, validate fields, etc.
}