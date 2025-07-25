package com.library_management;

import java.io.IOException;

import com.library_management.dao.implementation.ReportsDAOImplementation;

import javafx.fxml.FXML;

public class HomeController {
	 @FXML
	    private void bookManagement() throws IOException {
		 	new ReportsDAOImplementation().countOfBooksPerCategory();
	        App.setRoot("bookOptions");
	    }
	 
	 @FXML
	    private void memberManagement() throws IOException {
	        App.setRoot("memberOptions");
	    }
	 
	 @FXML
	    private void issueAndReturnBooks() throws IOException {
	        App.setRoot("IssueReturnMain");
	    }
}
