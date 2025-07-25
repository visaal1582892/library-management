package com.library_management.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library_management.dao.BookDAOInterface;
import com.library_management.domain.Book;
import com.library_management.domain.BookAvailability;
import com.library_management.domain.BookCategory;
import com.library_management.domain.BookStatus;
import com.library_management.exceptions.DatabaseException;
import com.library_management.utilities.DBConnection;

public class BookDAOImplementation implements BookDAOInterface {

	@Override
	public void addBook(Book book) throws DatabaseException {
		Connection conn=DBConnection.getConn();
		try {
			PreparedStatement psInsert=conn.prepareStatement("insert into lms.books(title,author,category) values(?,?,?)");
			psInsert.setString(1, book.getTitle());
			psInsert.setString(2, book.getAuthor());
			psInsert.setString(3, book.getCategory().getStringValue());
			psInsert.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public void updateBookDetails(Book oldBook,Book newBook) throws DatabaseException {
		Connection conn=DBConnection.getConn();
		String updateBooksQuery="update lms.books set title=?, author=?, category=?, status=? where book_id=?";
		String insertBooksLogQuery="insert into lms.books_log(book_id,title,author,category,status,availability) values(?,?,?,?,?,?)";
		try {
			PreparedStatement psInsertLog=conn.prepareStatement(insertBooksLogQuery);
			psInsertLog.setInt(1, oldBook.getBookId());
			psInsertLog.setString(2, oldBook.getTitle());
			psInsertLog.setString(3, oldBook.getAuthor());
			psInsertLog.setString(4, oldBook.getCategory().getStringValue());
			psInsertLog.setString(5, oldBook.getStatus().getStringValue());
			psInsertLog.setString(6, oldBook.getAvailability().getStringValue());
			psInsertLog.executeUpdate();
			PreparedStatement psUpdate=conn.prepareStatement(updateBooksQuery);
			psUpdate.setString(1, newBook.getTitle());
			psUpdate.setString(2, newBook.getAuthor());
			psUpdate.setString(3, newBook.getCategory().getStringValue());
			psUpdate.setString(4, newBook.getStatus().getStringValue());
			psUpdate.setInt(5, oldBook.getBookId());
			psUpdate.executeUpdate();
			
		} catch (SQLException e) {
			throw new DatabaseException("Error Occurred while updating data...");
		}
		
	}

	@Override
	public void updateBookAvailability(int id, String availability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBook(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Book> selectAllBooks() throws DatabaseException {
		List<Book> books=new ArrayList<>();
		Statement statement=DBConnection.getStatement();
		String selectQuery="select * from lms.books";
		try {
			ResultSet result=statement.executeQuery(selectQuery);
			while(result.next()) {
				int id=result.getInt(1);
				String title=result.getString(2);
				String author=result.getString(3);
				BookCategory category=BookCategory.getEnumConstant(result.getString(4));
				BookStatus status=BookStatus.getEnumConstant(result.getString(5));
				BookAvailability availability=BookAvailability.getEnumConstant(result.getString(6));
				
				Book currentBook=new Book(id,title,author,category,status,availability);
				books.add(currentBook);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		return books;
	}

	@Override
	public Book selectBookById(int id) throws DatabaseException {
		Book currentBook=null;
		Connection conn=DBConnection.getConn();
		String selectOneQuery="select * from lms.books where book_id=?";
		try {
			PreparedStatement psSelectOne=conn.prepareStatement(selectOneQuery);
			psSelectOne.setInt(1, id);
			psSelectOne.execute();
			ResultSet resultSet=psSelectOne.getResultSet();
			if(resultSet.next()) {
				int bookId=resultSet.getInt(1);
				String title=resultSet.getString(2);
				String author=resultSet.getString(3);
				BookCategory category=BookCategory.getEnumConstant(resultSet.getString(4));
				BookStatus status=BookStatus.getEnumConstant(resultSet.getString(5));
				BookAvailability availability=BookAvailability.getEnumConstant(resultSet.getString(6));
				
				currentBook=new Book(bookId,title,author,category,status,availability);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		return currentBook;
	}

	@Override
	public List<Book> selectAllMemberBooks(int memberId) throws DatabaseException {
		
		List<Book> memberBooks=new ArrayList<Book>();
		Statement statement=DBConnection.getStatement();
		String selectQuery="select b.* from books b join issue_records i on b.book_id=i.book_id join members m on i.member_id=m.member_id where i.member_id=?";
		try {
			ResultSet result=statement.executeQuery(selectQuery);
			while(result.next()) {
				int id=result.getInt(1);
				String title=result.getString(2);
				String author=result.getString(3);
				BookCategory category=BookCategory.getEnumConstant(result.getString(4));
				BookStatus status=BookStatus.getEnumConstant(result.getString(5));
				BookAvailability availability=BookAvailability.getEnumConstant(result.getString(6));
				
				Book currentBook=new Book(id,title,author,category,status,availability);
				memberBooks.add(currentBook);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		return memberBooks;
	}
	
	

}
