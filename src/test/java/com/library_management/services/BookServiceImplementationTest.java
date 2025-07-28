package com.library_management.services;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.implementation.BookServiceImplementation;
import com.library_management.utilities.DBConnection;


public class BookServiceImplementationTest {
	private BookServiceImplementation service;

    @Before
    public void setUp() {
        service = new BookServiceImplementation();
    }

//    @Test
//    public void testValidateAddBook_WithValidDetails_ShouldPass() throws Exception {
//        service.validateAddBook("Brain Rot", "Rohit Varma", BookCategory.FICTION);
//    }

    @Test(expected = InvalidDetailsException.class)
    public void testValidateAddBook_WithEmptyTitle_ShouldThrowException() throws Exception {
        service.validateAddBook("", "Visaal", BookCategory.NON_FICTION);
    }

    @Test(expected = InvalidDetailsException.class)
    public void testValidateAddBook_WithNullCategory_ShouldThrowException() throws Exception {
        service.validateAddBook("Love Story", "Rohit", null);
    }

    @Test(expected = InvalidDetailsException.class)
    public void testValidateAddBook_WithTooLongTitle_ShouldThrowException() throws Exception {
    	StringBuilder sb=new StringBuilder("");
    	for(int i=1; i<=256; i++) {
    		sb.append("R");
    	}
        String overflowedTitle = sb.toString();
        service.validateAddBook(overflowedTitle, "Author", BookCategory.SCIENCE_FICTION);
    }

//    @Test
//    public void testValidateViewAllBooks_ShouldReturnList() throws Exception {
//        assertNotNull(service.validateViewAllBooks());
//    }

    @Test(expected = InvalidDetailsException.class)
    public void testValidateUpdateBookDetails_WithEmptyFields_ShouldThrowException() throws Exception {
        service.validateUpdateBookDetails(1, "", "", BookCategory.FICTION, BookStatus.ACTIVE);
    }

//    @Test(expected = InvalidDetailsException.class)
//    public void testValidateUpdateBookDetails_WithUnchangedData_ShouldThrowException() throws Exception {
//        service.validateUpdateBookDetails(4, "aaaa", "aaaa", BookCategory.FICTION, BookStatus.ACTIVE);
//    }

//    @Test
//    public void testValidateUpdateBookDetails_WithChanges_ShouldPass() throws Exception {
//        service.validateUpdateBookDetails(5, "Ross", "Varma", BookCategory.MYSTERY, BookStatus.INACTIVE);
//    }
}
