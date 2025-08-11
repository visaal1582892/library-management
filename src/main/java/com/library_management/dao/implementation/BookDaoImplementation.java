package com.library_management.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library_management.constants.BookAvailability;
import com.library_management.constants.BookCategory;
import com.library_management.constants.BookStatus;
import com.library_management.dao.BookDaoInterface;
import com.library_management.domain.Book;
import com.library_management.exceptions.DatabaseException;
import com.library_management.utilities.DBConnection;

public class BookDaoImplementation implements BookDaoInterface {

	
//  Functional to insert logs 
  private void insertBookLog(Connection conn, Book book) throws SQLException, DatabaseException {
      String insertBooksLogQuery = 
          "INSERT INTO books_log(book_id, title, author, category, status, availability) VALUES (?, ?, ?, ?, ?, ?)";
      try (PreparedStatement psInsertLog = conn.prepareStatement(insertBooksLogQuery)) {
          psInsertLog.setInt(1, book.getBookId());
          psInsertLog.setString(2, book.getTitle());
          psInsertLog.setString(3, book.getAuthor());
          psInsertLog.setString(4, book.getCategory().getStringValue());
          psInsertLog.setString(5, book.getStatus().getStringValue());
          psInsertLog.setString(6, book.getAvailability().getStringValue());
          int count=psInsertLog.executeUpdate();
          if(count!=1) {
        	  throw new DatabaseException("Log Not Inserted Correctly...");
          }
      }catch (SQLException e) {
		throw new DatabaseException("Log Insertion Failed...");
	}catch (DatabaseException e) {
		throw e;
	}
  }
	
	@Override
	public int addBook(Book book) throws DatabaseException {
		Connection conn=DBConnection.getConn();
		int id=0;
		try (
	             PreparedStatement psInsert = conn.prepareStatement(
	                 "INSERT INTO books(title, author, category, status, availability) VALUES (?, ?, ?, ?, ?)",
	                 Statement.RETURN_GENERATED_KEYS)) {

	            psInsert.setString(1, book.getTitle());
	            psInsert.setString(2, book.getAuthor());
	            psInsert.setString(3, book.getCategory().getStringValue());
	            psInsert.setString(4, book.getStatus().getStringValue());
	            psInsert.setString(5, book.getAvailability().getStringValue());
	            int count=psInsert.executeUpdate();
	            if(count!=1) {
	            	throw new DatabaseException("Book Not Inserted Correctly...");
	            }

	            ResultSet rs = psInsert.getGeneratedKeys();
	            if (rs.next()) {
	                id = rs.getInt(1);
	            }
	        } catch (SQLException e) {
	            throw new DatabaseException("Failed to add book: " + e.getMessage());
	        }catch (DatabaseException e) {
				throw e;
			}
	        return id;
	}

//	@Override
//	public void updateBookDetails(Book oldBook,Book newBook) throws DatabaseException {
//		Connection conn=DBConnection.getConn();
//		String updateBooksQuery="update books set title=?, author=?, category=?, status=? where book_id=?";
//		String insertBooksLogQuery="insert into books_log(book_id,title,author,category,status,availability) values(?,?,?,?,?,?)";
//		try(
//				PreparedStatement psUpdate=conn.prepareStatement(updateBooksQuery);) {
//			conn.setAutoCommit(false);
//			
//			insertBookLog(conn, oldBook);
//			
//			psUpdate.setString(1, newBook.getTitle());
//			psUpdate.setString(2, newBook.getAuthor());
//			psUpdate.setString(3, newBook.getCategory().getStringValue());
//			psUpdate.setString(4, newBook.getStatus().getStringValue());
//			psUpdate.setInt(5, oldBook.getBookId());
//			psUpdate.executeUpdate();
//			conn.commit();
//			conn.setAutoCommit(true);
//		} catch (SQLException e) {
//			try {
//				conn.rollback();
//			}catch (SQLException ex) {
//				throw new DatabaseException("Update Details Rollback Failed...");
//			}
//			throw new DatabaseException("Error Occurred while updating data...");
//		}
//		
//	}
	
	@Override
	public void updateBookDetails(Book oldBook,Book newBook) throws DatabaseException {
		Connection conn=DBConnection.getConn();
		String updateBooksQuery="update books set title=?, author=?, category=? where book_id=?";
		try(
				PreparedStatement psUpdate=conn.prepareStatement(updateBooksQuery);) {
			conn.setAutoCommit(false);
			
			insertBookLog(conn, oldBook);
			
			psUpdate.setString(1, newBook.getTitle());
			psUpdate.setString(2, newBook.getAuthor());
			psUpdate.setString(3, newBook.getCategory().getStringValue());
			psUpdate.setInt(4, oldBook.getBookId());
			int count=psUpdate.executeUpdate();
			if(count!=1) {
				throw new DatabaseException("Book Not Updated Correctly...");
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();
			}catch (SQLException ex) {
				throw new DatabaseException("Update Details Rollback Failed...");
			}
			throw new DatabaseException("Error Occurred while updating data...");
		}catch (DatabaseException e) {
			throw e;
		}
		
	}

	@Override
	public void updateBookAvailability(Book oldBook, String availability, Connection argConn) throws DatabaseException {
	    
//	    checking if Book is active or not
	    if (!BookStatus.ACTIVE.equals(oldBook.getStatus())) {
	        throw new DatabaseException("Book Cannot Be Issued Since It Is Inactive");
	    }

	    Connection conn = argConn==null?DBConnection.getConn():argConn;
	    String updateAvailabilityQuery = "UPDATE books SET availability=? WHERE book_id=?";
	    try (PreparedStatement psUpdate = conn.prepareStatement(updateAvailabilityQuery)) {
	        conn.setAutoCommit(false);

	        insertBookLog(conn, oldBook);

	        psUpdate.setString(1, availability);
	        psUpdate.setInt(2, oldBook.getBookId());
	        int count=psUpdate.executeUpdate();
	        if (count!=1) {
				throw new DatabaseException("Book Availability Not Updated Correctly...");
			}

	        conn.commit();
	        conn.setAutoCommit(true);
	    } catch (SQLException e) {
	        try {
	            conn.rollback();
	        } catch (SQLException ex) {
	            throw new DatabaseException("Update Availability Rollback Failed...");
	        }
	        throw new DatabaseException("Failed To Update Availability...");
	    }catch (DatabaseException e) {
			throw e;
		}
	}


	@Override
	public void deleteBook(Book oldBook) throws DatabaseException {
		Connection conn=DBConnection.getConn();
		String updateStatusQuery = "UPDATE books SET status=? WHERE book_id=? and availability=?";
		try (PreparedStatement psUpdateStatus = conn.prepareStatement(updateStatusQuery)) {
			conn.setAutoCommit(false);
			
			insertBookLog(conn, oldBook);
			
            psUpdateStatus.setString(1, BookStatus.INACTIVE.getStringValue());
            psUpdateStatus.setInt(2, oldBook.getBookId());
            psUpdateStatus.setString(3, BookAvailability.AVAILABLE.getStringValue());
            int count=psUpdateStatus.executeUpdate();
            if (count!=1) {
            	throw new DatabaseException("An Issued Book Cannot Be Deleted, Not deleted Correcly...");
			}
            
            conn.commit();
            conn.setAutoCommit(true);
        }catch(SQLException e) {
			try {
				conn.rollback();
			}catch (SQLException ex) {
				throw new DatabaseException("Book Deletion Rollback Failed...");
			}
			throw new DatabaseException("Failed To Delete Book...");
		}
		
	}

	@Override
	public List<Book> selectAllBooks() throws DatabaseException {
		List<Book> books=new ArrayList<>();
		Statement statement=DBConnection.getStatement();
		String selectQuery = "SELECT * FROM books WHERE status = 'A'";
		try(ResultSet result=statement.executeQuery(selectQuery);) {
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
		String selectOneQuery = "SELECT * FROM books WHERE book_id=? and status='A'";
		try(PreparedStatement psSelectOne=conn.prepareStatement(selectOneQuery);) {
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
		
		Connection conn=DBConnection.getConn();
		List<Book> memberBooks=new ArrayList<Book>();
		try {
			
			String selectQuery = 
		            "SELECT b.* FROM books b JOIN issue_records i ON b.book_id = i.book_id " +
		            "WHERE i.member_id = ? AND i.status = 'I' AND b.status = 'A'";
			PreparedStatement psSelect=conn.prepareStatement(selectQuery);
			psSelect.setInt(1, memberId);
			ResultSet result=psSelect.executeQuery();
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
