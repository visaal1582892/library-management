package com.library_management.controllers.issue_and_return_books;

import java.io.IOException;

import com.library_management.services.IssueRecordServiceInterface;
import com.library_management.services.implementation.IssueRecordServiceImplementation;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ReturnBookController {
	
	IssueRecordServiceInterface service = new IssueRecordServiceImplementation();
	
	@FXML
	private TextField issueIdField;

	@FXML
    private void switchToReturnBookSubmit() throws IOException {
		int issueId = Integer.parseInt(issueIdField.getText().trim());
		service.returnBook(issueId);
    }
}
