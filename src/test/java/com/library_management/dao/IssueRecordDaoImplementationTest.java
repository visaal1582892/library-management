package com.library_management.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.library_management.dao.implementation.IssueRecordDAOImplementation;
import com.library_management.domain.IssueRecord;

class IssueRecordDAOImplementationTest {

	@Test
	void testIssueBook() {
		//fail("Not yet implemented");
		IssueRecord issue = new IssueRecord(1, 1, LocalDate.now());
		String print_message = new IssueRecordDAOImplementation().issueBook(issue);
		assertEquals("Book issued successfully",print_message);
	}

	@Test
	void testReturnBook() {
		//fail("Not yet implemented");
		String print_message = new IssueRecordDAOImplementation().returnBook(1, 1);
		assertEquals("Book returned successfully",print_message);
	}

	@Test
	void testGetAllIssues() {
		//fail("Not yet implemented");
		List<IssueRecord> all = new IssueRecordDAOImplementation().getAllIssues();
		assertNotNull(all);
	}

}
