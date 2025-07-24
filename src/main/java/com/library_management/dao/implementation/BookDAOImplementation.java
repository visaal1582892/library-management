package com.library_management.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.library_management.dao.BookDAOInterface;
import com.library_management.domain.Book;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBookAvailability(int id, String availability) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBook(int id) {
		// TODO Auto-generated method stub
		
	}

}
