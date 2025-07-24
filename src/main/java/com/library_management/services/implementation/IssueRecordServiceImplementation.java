package com.library_management.services.implementation;

import java.util.List;

import com.library_management.dao.IssueRecordDAOInterface;
import com.library_management.dao.implementation.IssueRecordDAOImplementation;
import com.library_management.domain.IssueRecord;
import com.library_management.services.IssueRecordServiceInterface;

public class IssueRecordServiceImplementation implements IssueRecordServiceInterface {

	IssueRecordDAOInterface dao = new IssueRecordDAOImplementation();

	@Override
	public void issueBook(IssueRecord issue) {
		dao.issueBook(issue);
	}

	@Override
	public void returnBook(int issueId) {
		dao.returnBook(issueId);
	}

	@Override
	public List<IssueRecord> getAllIssues() {
		return dao.getAllIssues();
	}

	@Override
	public List<IssueRecord> getOverdueBooks() {
		return dao.getOverdueBooks();
	}

	@Override
	public List<IssueRecord> getActiveIssuedBooks() {
		return dao.getActiveIssuedBooks();
	}

}
