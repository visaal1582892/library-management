package com.library_management.dao;

import java.util.List;
import java.util.Map;

import com.library_management.domain.IssueRecord;

public interface ReportsDAOInterface {
	public Map<Object, Long> countOfBooksPerCategory();

	List<IssueRecord> getOverdueBooks();

	List<List<String>> getActiveIssuedBooks();
}
