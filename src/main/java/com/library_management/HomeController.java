package com.library_management;

import java.io.IOException;

import javafx.fxml.FXML;

public class HomeController {
	 @FXML
	    private void bookManagement() throws IOException {
	        App.setRoot("home");
	    }
	 
	 @FXML
	    private void memberManagement() throws IOException {
	        App.setRoot("home");
	    }
	 
	 @FXML
	    private void issueAndReturnBooks() throws IOException {
	        App.setRoot("home");
	    }
}
