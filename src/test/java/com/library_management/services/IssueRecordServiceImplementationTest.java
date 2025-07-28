package com.library_management.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.library_management.domain.IssueRecord;
import com.library_management.services.implementation.IssueRecordServiceImplementation;

class IssueRecordServiceImplementationTest {

	private IssueRecordServiceImplementation service;

	@Before
	public void setUp() {
		service = new IssueRecordServiceImplementation();
	}

	@Test
	void testGetAllIssues() {
		// fail("Not yet implemented");
		List<IssueRecord> all = service.getAllIssues();
		assertNotNull(all);
	}

	@Test
	void testGetOverdueBooks() {
		// fail("Not yet implemented");
		List<IssueRecord> overdue = service.getOverdueBooks();
		assertNotNull(overdue);
	}

	@Test
	void testGetActiveIssuedBooks() {
		// fail("Not yet implemented");
		List<List<String>> activeIssues = service.getActiveIssuedBooks();
		assertNotNull(activeIssues);
	}

}
