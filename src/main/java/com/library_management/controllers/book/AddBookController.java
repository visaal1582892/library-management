package com.library_management.controllers.book;

import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddBookController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

//    @FXML
//    private ComboBox<String> statusComboBox;
//
//    @FXML
//    private ComboBox<String> availabilityComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    public void initialize() {
        // Adding categories from enum values.
    	for(BookCategory enumValue:BookCategory.values()) {
    		categoryComboBox.getItems().addAll(enumValue.getStringValue());
    	}
    	
//    	for(BookStatus enumValue:BookStatus.values()) {
//    		statusComboBox.getItems().addAll(enumValue.getStringValue());
//    	}
//    	
//    	for(BookAvailability enumValue:BookAvailability.values()) {
//    		availabilityComboBox.getItems().addAll(enumValue.getStringValue());
//    	}
    }


   
   
    // Methods to handle events
    @FXML
    public void addBook() {
    	String title=titleField.getText();
    	String author=authorField.getText();
    	String categoryCombo=categoryComboBox.getValue();
    	String category=(categoryCombo==null)?"":categoryCombo;
//    	String status=statusComboBox.getValue();
//    	String availability=availabilityComboBox.getValue();
    	
//    	System.out.println(title+" "+author+" "+category+" "+status+" "+availability);
    	System.out.println(title+" "+author+" "+category);
    }

}

