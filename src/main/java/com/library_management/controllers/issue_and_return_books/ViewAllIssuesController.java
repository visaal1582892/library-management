package com.library_management.controllers.issue_and_return_books;

import java.util.List;

import com.library_management.domain.IssueRecord;
import com.library_management.services.IssueRecordServiceInterface;
import com.library_management.services.implementation.IssueRecordServiceImplementation;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ViewAllIssuesController {
	
	IssueRecordServiceInterface service = new IssueRecordServiceImplementation();
	
	 @FXML
	    private TableView<IssueRecord> tableView;
	 
	 @FXML
	    public void initialize() {
		 
	        List<IssueRecord> data = service.getAllIssues();

	        ViewTable(data);
	    }

	    private void ViewTable(List<IssueRecord> data) {
	    	
	        if (data.isEmpty()) return;

	     
	    }
}
