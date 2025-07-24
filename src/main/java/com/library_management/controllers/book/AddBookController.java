package com.library_management.controllers.book;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;
import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.implementation.BookServiceImplementation;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private ComboBox<BookCategory> categoryComboBox;
    
    @FXML
    private Text message;

    @FXML
    public void initialize() {
        // Adding categories from enum values.
    	categoryComboBox.getItems().addAll(BookCategory.values());
    	
//    	statusComboBox.getItems().addAll(BookStatus.values());
//    	
//    	statusComboBox.getItems().addAll(BookAvailability.values());
    }

    public void clearForm() {
    	titleField.setText("");
		authorField.setText("");
		categoryComboBox.setValue(null);
    }
   
   
    // Methods to handle events
    @FXML
    public void addBook() throws InterruptedException {
    	String title=titleField.getText();
    	String author=authorField.getText();
    	BookCategory category=categoryComboBox.getSelectionModel().getSelectedItem();
//    	String status=statusComboBox.getValue();
//    	String availability=availabilityComboBox.getValue();
    	
//    	System.out.println(title+" "+author+" "+category+" "+status+" "+availability);
//    	System.out.println(title+" "+author+" "+category);
    	try {
			new BookServiceImplementation().validateAddBook(title, author, category);
			message.setText("Book Added Succesfully...");
			message.setFill(Color.GREEN);
			clearForm();
			PauseTransition pauseTransition=new PauseTransition(Duration.seconds(3));
			pauseTransition.setOnFinished(event -> message.setText(""));
			pauseTransition.play();
		} catch (InvalidDetailsException|DatabaseException e) {
			
			message.setText(e.getMessage());
			message.setFill(Color.RED);
			PauseTransition pauseTransition=new PauseTransition(Duration.seconds(3));
			pauseTransition.setOnFinished(event -> message.setText(""));
			pauseTransition.play();
		}
    }

}

