package com.library_management.dao;

import java.util.List;
import com.library_management.domain.IssueRecord;

public interface IssueRecordDAOInterface {
	
	void issueBook(IssueRecord issue);
	void returnBook(int issueId);
	List<IssueRecord> getAllIssues();
	List<IssueRecord> getOverdueBooks();
	List<IssueRecord> getActiveIssuedBooks();
}
