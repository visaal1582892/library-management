package com.library_management.services;

import java.util.List;
import com.library_management.domain.IssueRecord;

public interface IssueRecordServiceInterface {

	void issueBook(IssueRecord issue);
	void returnBook(int issueId);
	List<IssueRecord> getAllIssues();
	List<IssueRecord> getOverdueBooks();
	List<List<String>> getActiveIssuedBooks();
}
