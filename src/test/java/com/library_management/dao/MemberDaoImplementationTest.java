package com.library_management.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import com.library_management.dao.implementation.BookDAOImplementation;
import com.library_management.dao.implementation.MemberDAOImplementation;
import com.library_management.domain.Book;
import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.domain.Member;
import com.library_management.exceptions.DatabaseException;
import com.library_management.utilities.DBConnection;

public class MemberDaoImplementationTest {
    private MemberDAOImplementation dao;
    private int generatedId;

    @Before
    public void setUp() throws Exception {
    	DBConnection.connectToDB("jdbc:mysql://localhost:3306/lms_test");
        dao=new MemberDAOImplementation();
        
        this.generatedId=dao.insertMember(new Member("test", "test3@gmail.com","9899999989","M","seeeyhas"));
    }

    @Test
    public void testAddBook_ShouldPass() throws DatabaseException, SQLException {
    	dao.insertMember(new Member("tarun","tarun12345@gmail.com","9397626811","M","seethampetys"));
    }

//    
//    @Test
//    public void testUpdateBookAvailability_ShouldPass() throws DatabaseException{
//    	System.out.println(this.generatedId);
//    	Book oldBook=new Book(generatedId, "test", "test", BookCategory.FICTION, BookStatus.ACTIVE, BookAvailability.AVAILABLE);
//    	String newAvailability="I";
//    	dao.updateBookAvailability(oldBook, newAvailability);
//    }
//    
//    @Test
//    public void testSelectAllBooks_ShouldPass_ShouldReturnList() throws DatabaseException{
//    	Object result=dao.selectAllBooks();
//    	assertNotNull(result);
//    	assertTrue(result instanceof List);
//    }
//    
////    success Case
//    @Test
//    public void testSelectBookById_ShouldPass_ShouldReturnBook() throws DatabaseException{
//    	Object result=dao.selectBookById(this.generatedId);
//    	assertNotNull(result);
//    	assertTrue(result instanceof Book);
//    }
//    
////    Fail cAse
//    @Test
//    public void testSelectBookById_ShouldPass_ShouldReturnNull() throws DatabaseException{
//    	Object result=dao.selectBookById(9999);
//    	assertNull(result);
//    }
//    
//    @Test
//    public void testDeleteBook_ShoulPass() throws DatabaseException{
//    	Book oldBook=new Book(this.generatedId, "test", "test", BookCategory.FICTION, BookStatus.ACTIVE, BookAvailability.AVAILABLE);
//    	dao.deleteBook(oldBook);
//    }

    @After
    public void tearDown() throws Exception {
    	dao.deleteMember(this.generatedId);
    	DBConnection.closeStatement();
        DBConnection.closeConn();
    }

}
    