package com.library_management.controllers.issue_and_return_books;

import com.library_management.services.IssueRecordServiceInterface;
import com.library_management.services.implementation.IssueRecordServiceImplementation;
import javafx.fxml.FXML;

public class ViewAllIssuesController {

	IssueRecordServiceInterface service = new IssueRecordServiceImplementation();


	@FXML
	public void initialize() {

		
	}

}
