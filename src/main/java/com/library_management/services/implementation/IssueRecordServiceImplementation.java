package com.library_management.services.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
	public void returnBook(int memberId, int bookId) {
		dao.returnBook(memberId, bookId);
	}

	@Override
	public List<IssueRecord> getAllIssues() {
		return dao.getAllIssues();
	}

	@Override
	public List<IssueRecord> getOverdueBooks() {
		//return dao.getOverdueBooks();
		List<IssueRecord> overdue = getAllIssues().stream()
				.filter(b -> b.getReturnDate().isBefore(LocalDate.now().minusDays(17)))
				.collect(Collectors.toList());
		return overdue;
	}

	@Override
	public List<List<String>> getActiveIssuedBooks() {
		return dao.getActiveIssuedBooks();
	}

}
