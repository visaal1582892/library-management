package com.library_management.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.library_management.dao.implementation.IssueRecordDAOImplementation;
import com.library_management.domain.IssueRecord;
import com.library_management.services.implementation.IssueRecordServiceImplementation;

class IssueRecordServiceImplementationTest {

	@Test
	void testIssueBook() {
		//fail("Not yet implemented");
	}

	@Test
	void testReturnBook() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetAllIssues() {
		//fail("Not yet implemented");
		List<IssueRecord> all = new IssueRecordServiceImplementation().getAllIssues();
		assertNotNull(all);
	}

	@Test
	void testGetOverdueBooks() {
		//fail("Not yet implemented");
		List<IssueRecord> overdue = new IssueRecordServiceImplementation().getOverdueBooks();
        assertNotNull(overdue);
	}

	@Test
	void testGetActiveIssuedBooks() {
		//fail("Not yet implemented");
		List<List<String>> activeIssues = new IssueRecordServiceImplementation().getActiveIssuedBooks();
		assertNotNull(activeIssues);
	}

}
