package com.library_management.controllers.book;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;
import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.implementation.BookServiceImplementation;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
    private Text message;

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
    public void addBook() throws InterruptedException {
    	String title=titleField.getText();
    	String author=authorField.getText();
    	String categoryCombo=categoryComboBox.getValue();
    	String category=(categoryCombo==null)?"":categoryCombo;
//    	String status=statusComboBox.getValue();
//    	String availability=availabilityComboBox.getValue();
    	
//    	System.out.println(title+" "+author+" "+category+" "+status+" "+availability);
//    	System.out.println(title+" "+author+" "+category);
    	try {
			new BookServiceImplementation().validateAddBook(title, author, category);
		} catch (InvalidDetailsException e) {
			
			message.setText(e.getMessage());
			message.setFill(Color.RED);
		}
    }

}

