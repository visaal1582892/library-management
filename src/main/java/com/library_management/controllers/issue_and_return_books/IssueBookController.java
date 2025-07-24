package com.library_management.controllers.issue_and_return_books;

import java.io.IOException;
import java.time.LocalDate;

import com.library_management.domain.IssueRecord;
import com.library_management.domain.IssueRecordStatus;
import com.library_management.services.IssueRecordServiceInterface;
import com.library_management.services.implementation.IssueRecordServiceImplementation;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IssueBookController {
	
	IssueRecordServiceInterface service = new IssueRecordServiceImplementation();
	@FXML
	private TextField memberIdField;
	@FXML
	private TextField bookIdField;
	
	@FXML
    private void switchToIssueBookSubmit() throws IOException {
		int memberId = Integer.parseInt(memberIdField.getText().trim());
        int bookId = Integer.parseInt(bookIdField.getText().trim());
        IssueRecord issue = new IssueRecord(memberId, bookId, IssueRecordStatus.I, LocalDate.now());
        service.issueBook(issue);
    }
}
