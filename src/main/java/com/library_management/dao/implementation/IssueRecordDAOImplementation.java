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

import com.library_management.dao.IssueRecordDAOInterface;
import com.library_management.domain.IssueRecord;
import com.library_management.domain.IssueRecordStatus;
import com.library_management.utilities.DBConnection;

public class IssueRecordDAOImplementation implements IssueRecordDAOInterface {

	@Override
	public void issueBook(IssueRecord issue) {
		Connection conn = null;
		try {
			conn = DBConnection.getConn();

			// Check if member exists
			String memberSql = "SELECT COUNT(*) FROM members WHERE member_id = ?";
			try (PreparedStatement memberStmt = conn.prepareStatement(memberSql)) {
				memberStmt.setInt(1, issue.getMemberId());
				ResultSet rs = memberStmt.executeQuery();
				if (!rs.next()) {
					System.out.println("Member does not exist");
					return;
				}
			}

			// Check book status
			String bookSql = "SELECT status, availability FROM books WHERE book_id = ?";
			try (PreparedStatement bookStmt = conn.prepareStatement(bookSql)) {
				bookStmt.setInt(1, issue.getBookId());
				ResultSet rs = bookStmt.executeQuery();
				if (!rs.next()) {
					System.out.println("Book does not exist");
					return;
				}
				
				char status = rs.getString("status").charAt(0);
				char availability = rs.getString("availability").charAt(0);

				if (status != 'A') {
					System.out.println("Book is not active");
					return;
				}
				if (availability != 'A') {
					System.out.println("Book is not available for issue");
					return;
				}
			}

			// Insert issue record
			String issueSql = "INSERT INTO issue_records (book_id, member_id, status, issue_date, return_date) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(issueSql)) {
				pstmt.setInt(1, issue.getBookId());
				pstmt.setInt(2, issue.getMemberId());
				pstmt.setString(3, String.valueOf(issue.getStatus()));
				pstmt.setDate(4, Date.valueOf(issue.getIssueDate()));
				pstmt.setDate(5, issue.getReturnDate() != null ? Date.valueOf(issue.getReturnDate()) : null);
				pstmt.executeUpdate();
			}

			// Update book availability
			String updateSql = "UPDATE books SET availability = 'I' WHERE book_id = ?";
			try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
				updateStmt.setInt(1, issue.getBookId());
				int rows = updateStmt.executeUpdate();
				if (rows == 0) {
					System.out.println("Failed to update book availability");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void returnBook(int issueId) {
		Connection conn = null;
		try {
			conn = DBConnection.getConn();

			// Verify issue record and get BookId
			String selectSql = "SELECT book_id FROM issue_records WHERE issue_id = ? AND status = 'I'";
			int bookId;
			try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
				selectStmt.setInt(1, issueId);
				ResultSet rs = selectStmt.executeQuery();
				if (!rs.next()) {
					System.out.println("Issue record not found or already returned");
					return;
				}
				bookId = rs.getInt("BookId");
			}

			// Update issue record
			String updateIssueSql = "UPDATE issue_records SET status = 'R', return_date = ? WHERE issue_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(updateIssueSql)) {
				pstmt.setDate(1, Date.valueOf(LocalDate.now()));
				pstmt.setInt(2, issueId);
				int rows = pstmt.executeUpdate();
				if (rows == 0) {
					System.out.println("Failed to update issue record");
					return;
				}
				logIssueAction(issueId);
			}

			// Update book availability
			String updateBookSql = "UPDATE books SET availability = 'A' WHERE book_id = ?";
			try (PreparedStatement updateStmt = conn.prepareStatement(updateBookSql)) {
				updateStmt.setInt(1, bookId);
				int rows = updateStmt.executeUpdate();
				if (rows == 0) {
					System.out.println("Failed to update book availability");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<IssueRecord> getOverdueBooks() {
		List<IssueRecord> overdue = new ArrayList<>();
		String sql = "SELECT * FROM issue_records WHERE status = 'I' AND issue_date < ?";
		try (Connection conn = DBConnection.getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setDate(1, Date.valueOf(LocalDate.now().minusDays(17)));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				IssueRecord issue = new IssueRecord();
				issue.setIssueId(rs.getInt("issue_id"));
				issue.setBookId(rs.getInt("book_id"));
				issue.setMemberId(rs.getInt("member_id"));
				issue.setStatus(IssueRecordStatus.valueOf(rs.getString("status")));
				issue.setIssueDate(rs.getDate("issue_date").toLocalDate());
				issue.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return overdue;
	}

	@Override
	public List<IssueRecord> getAllIssues() {
		List<IssueRecord> issues = new ArrayList<>();
		String sql = "SELECT * FROM issue_records";
		try (Connection conn = DBConnection.getConn();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
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
	public List<List<String>> getActiveIssuedBooks() {
		List<List<String>> activeIssues = new ArrayList<>();
		String sql = "SELECT * FROM members m JOIN issue_records ir ON m.member_id = ir.member_id JOIN books b ON ir.book_id = b.book_id WHERE ir.status = 'I' AND b.status = 'A'";
		try (Connection conn = DBConnection.getConn();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				List<String> issue = new ArrayList<>();
				issue.add(String.valueOf(rs.getInt("issue_id")));
				issue.add(String.valueOf(rs.getInt("book_id")));
				issue.add(String.valueOf(rs.getInt("member_id")));
				issue.add(rs.getString("status"));
				issue.add(rs.getDate("issue_date").toString());
				issue.add(rs.getDate("return_date") != null ? rs.getDate("return_date").toString() : null);
				activeIssues.add(issue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activeIssues;
	}

	private void logIssueAction(int issueId) {
		String selectSql = "SELECT * FROM issue_records where issue_id = ?";
		String logSql = "INSERT INTO issue_records_log (issue_id, book_id, member_id, status, issue_date, return_date) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConn();
				PreparedStatement pstmt = conn.prepareStatement(logSql);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(selectSql)) {
			pstmt.setInt(1, issueId);
			pstmt.setInt(2, rs.getInt("book_id"));
			pstmt.setInt(3, rs.getInt("member_id"));
			pstmt.setString(4, rs.getString("status"));
			pstmt.setDate(5, rs.getDate("issue_date"));
			pstmt.setDate(6, rs.getDate("return_date"));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
