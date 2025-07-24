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
			conn = DBConnection.getConnection();

			// Check if member exists
			String memberSql = "SELECT COUNT(*) FROM members WHERE member_id = ?";
			try (PreparedStatement memberStmt = conn.prepareStatement(memberSql)) {
				memberStmt.setInt(1, issue.getMemberId());
				ResultSet rs = memberStmt.executeQuery();
				if (!rs.next() || rs.getInt(1) == 0) {
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

			// Check if member already has this book
			String existingSql = "SELECT COUNT(*) FROM issue_records WHERE member_id = ? AND book_id = ? AND status = 'I'";
			try (PreparedStatement existingStmt = conn.prepareStatement(existingSql)) {
				existingStmt.setInt(1, issue.getMemberId());
				existingStmt.setInt(2, issue.getBookId());
				ResultSet rs = existingStmt.executeQuery();
				if (rs.next() && rs.getInt(1) > 0) {
					System.out.println("Member already has this book issued");
					return;
				}
			}

			// Insert issue record
			String issueSql = "INSERT INTO issue_records (book_id, member_id, status, issue_date, return_date) VALUES (?, ?, ?, ?, ?)";
			int issueId;
			try (PreparedStatement pstmt = conn.prepareStatement(issueSql)) {
				pstmt.setInt(1, issue.getBookId());
				pstmt.setInt(2, issue.getMemberId());
				pstmt.setString(3, String.valueOf(issue.getStatus()));
				pstmt.setDate(4, Date.valueOf(issue.getIssueDate()));
				pstmt.setDate(5, issue.getReturnDate() != null ? Date.valueOf(issue.getReturnDate()) : null);
				pstmt.executeUpdate();

			}

			// Update book availability
			String updateSql = "UPDATE books SET availability = 'I' WHERE BookId = ?";
			try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
				updateStmt.setInt(1, issue.getBookId());
				int rows = updateStmt.executeUpdate();
				if (rows == 0) {
					System.out.println("Failed to update book availability");
				}
			}

			conn.commit();
		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void returnBook(int issueId) {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();

			// Verify issue record and get BookId
			String selectSql = "SELECT book_id FROM issue_records WHERE issue_id = ? AND satus = 'I'";
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
			String updateBookSql = "UPDATE books SET availability = 'A' WHERE BookId = ?";
			try (PreparedStatement updateStmt = conn.prepareStatement(updateBookSql)) {
				updateStmt.setInt(1, bookId);
				int rows = updateStmt.executeUpdate();
				if (rows == 0) {
					System.out.println("Failed to update book availability");
				}
			}

			conn.commit();
		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public List<IssueRecord> getOverdueBooks() {
		List<IssueRecord> overdue = new ArrayList<>();
		String sql = "SELECT * FROM issue_records WHERE status = 'I' AND issue_date < ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
				overdue.add(issue);
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
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				IssueRecord issue = new IssueRecord();
				issue.setIssueId(rs.getInt("issue_id"));
				issue.setBookId(rs.getInt("book_id"));
				issue.setMemberId(rs.getInt("member_id"));
				issue.setStatus(IssueRecordStatus.valueOf(rs.getString("status")));
				issue.setIssueDate(rs.getDate("issue_date").toLocalDate());
				issue.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("ReturnDate").toLocalDate() : null);
				issues.add(issue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return issues;
	}

	@Override
	public List<IssueRecord> getActiveIssuedBooks() {
		List<IssueRecord> activeIssues = new ArrayList<>();
		String sql = "SELECT DISTINCT m.MemberId, m.Name, m.Email, m.Mobile, m.Gender, m.Address\r\n"
				+ "FROM members m\r\n"
				+ "JOIN issue_records ir ON m.MemberId = ir.MemberId\r\n"
				+ "JOIN books b ON ir.BookId = b.BookId\r\n"
				+ "WHERE ir.Status = 'I' \r\n"
				+ "  AND b.Status = 'A';\r\n"
				+ "";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				IssueRecord issue = new IssueRecord();
				issue.setIssueId(rs.getInt("issue_id"));
				issue.setBookId(rs.getInt("book_id"));
				issue.setMemberId(rs.getInt("member_id"));
				issue.setStatus(IssueRecordStatus.valueOf(rs.getString("status")));
				issue.setIssueDate(rs.getDate("issue_date").toLocalDate());
				issue.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("ReturnDate").toLocalDate() : null);
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
		try (Connection conn = DBConnection.getConnection();
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
