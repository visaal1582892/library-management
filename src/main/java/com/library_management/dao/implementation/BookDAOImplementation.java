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
	public void updateBookDetails(int id, Book book) {
		Connection conn=DBConnection.getConn();
		String updateQuery="update ";
		
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
			psSelectOne.executeUpdate();
			ResultSet resultSet=psSelectOne.getResultSet();
			if(resultSet.next()) {
				int bookId=resultSet.getInt(1);
				String title=resultSet.getString(2);
				String author=resultSet.getString(3);
				BookCategory category=BookCategory.getEnumConstant(resultSet.getString(4));
				BookStatus status=BookStatus.getEnumConstant(resultSet.getString(5));
				BookAvailability availability=BookAvailability.getEnumConstant(resultSet.getString(6));
				
				currentBook=new Book(id,title,author,category,status,availability);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		return currentBook;
	}
	
	

}
