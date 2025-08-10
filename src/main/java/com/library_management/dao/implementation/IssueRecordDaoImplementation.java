package com.library_management.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.library_management.constants.BookAvailability;
import com.library_management.constants.BookCategory;
import com.library_management.constants.BookStatus;
import com.library_management.dao.IssueRecordDaoInterface;
import com.library_management.dao.BookDaoInterface;
import com.library_management.dao.MemberDaoInterface;
import com.library_management.domain.Book;
import com.library_management.domain.IssueRecord;
import com.library_management.domain.IssueRecordStatus;
import com.library_management.exceptions.DatabaseException;
import com.library_management.utilities.DBConnection;

public class IssueRecordDaoImplementation implements IssueRecordDaoInterface {

	@Override
	public String issueBook(IssueRecord issue) {
		Connection conn = DBConnection.getConn();
		try {

			MemberDaoInterface memberDao = new MemberDaoImplementation();
			BookDaoInterface bookDao = new BookDaoImplementation();

			if (memberDao.selectMemberById(issue.getMemberId()) == null) {
				return "Member does not exist";
			}

			Book book = bookDao.selectBookById(issue.getBookId());
			System.out.println("Book " +book);
			if (book == null) {
				return "Book does not exist";
			}

			if (book.getStatus() != BookStatus.ACTIVE) {
				return "Book is not active";
			}
			if (book.getAvailability() != BookAvailability.AVAILABLE) {
				return "Book is not available for issue";
			}

			String issueSql = "INSERT INTO issue_records (book_id, member_id, status, issue_date, return_date) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(issueSql)) {
				issue.setStatus(IssueRecordStatus.I);
				pstmt.setInt(1, issue.getBookId());
				pstmt.setInt(2, issue.getMemberId());
				pstmt.setString(3, String.valueOf(issue.getStatus()));
				pstmt.setDate(4, Date.valueOf(issue.getIssueDate()));
				pstmt.setDate(5, issue.getReturnDate() != null ? Date.valueOf(issue.getReturnDate()) : null);
				pstmt.executeUpdate();
			}

			bookDao.updateBookAvailability(book, "I", conn);

			return "Book issued successfully";

		} catch (SQLException e) {
			e.printStackTrace();
			return "Failed to issue book: " + e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return "Failed to update book: " + e.getMessage();
		}
	}

	@Override
	public String returnBook(int memberId, int bookId) {
		Connection conn = DBConnection.getConn();
		try {
			BookDaoInterface bookDao = new BookDaoImplementation();
			MemberDaoInterface memberDao = new MemberDaoImplementation();

			if (memberDao.selectMemberById(memberId) == null) {
				return "Member does not exist";
			}

			String selectSql = "SELECT * FROM issue_records WHERE member_id = ? AND book_id = ? AND status = 'I'";
			int issueId = -1;
			try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
				selectStmt.setInt(1, memberId);
				selectStmt.setInt(2, bookId);
				ResultSet rs = selectStmt.executeQuery();
				if (!rs.next()) {
					return "Issue record not found or already returned";
				}
				issueId = rs.getInt("issue_id");
			}

			logIssue(issueId);

			String updateIssueSql = "UPDATE issue_records SET status = 'R', return_date = ? WHERE issue_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(updateIssueSql)) {
				pstmt.setDate(1, Date.valueOf(LocalDate.now()));
				pstmt.setInt(2, issueId);
				if (pstmt.executeUpdate() == 0) {
					return "Failed to update issue record";
				}
			}

			Book book = bookDao.selectBookById(bookId);
			if (book == null) {
				return "Book not found for availability update";
			}
			bookDao.updateBookAvailability(book, "A", conn);

			return "Book returned successfully";

		} catch (SQLException e) {
			e.printStackTrace();
			return "Failed to return book: " + e.getMessage();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return "Failed to update book: " + e.getMessage();
		}
	}

	@Override
	public List<IssueRecord> getAllIssues() {
		List<IssueRecord> issues = new ArrayList<>();
		String sql = "SELECT * FROM issue_records";
		Connection conn = DBConnection.getConn();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				IssueRecord issue = new IssueRecord();
				issue.setIssueId(rs.getInt("issue_id"));
				issue.setBookId(rs.getInt("book_id"));
				issue.setMemberId(rs.getInt("member_id"));
				issue.setStatus(IssueRecordStatus.valueOf(rs.getString("status")));
				issue.setIssueDate(rs.getDate("issue_date").toLocalDate());
				issue.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null);
				issues.add(issue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return issues;
	}

	@Override
	public List<List<String>> getStatusTable() {
		List<List<String>> activeIssues = new ArrayList<>();
		String sql = "SELECT m.member_id, m.name, b.title, ir.status as issue_status, b.status as book_status FROM members m JOIN issue_records ir ON m.member_id = ir.member_id JOIN books b ON ir.book_id = b.book_id";
		try {
			Connection conn = DBConnection.getConn();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				List<String> issue = new ArrayList<>();
				issue.add(String.valueOf(rs.getInt("member_id")));
				issue.add(rs.getString("name"));
				issue.add(rs.getString("title"));
				issue.add(rs.getString("issue_status"));
				issue.add(rs.getString("book_status"));
				activeIssues.add(issue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activeIssues;
	}

	private void logIssue(int issueId) {
		String logSql = "INSERT INTO issue_records_log (issue_id, book_id, member_id, status, issue_date, return_date) SELECT * FROM issue_records WHERE issue_records.issue_id = ?";
		Connection conn = DBConnection.getConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(logSql);
			pstmt.setInt(1, issueId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Inserting log");
	}

}
