package com.library_management.controllers.issue_and_return_books;

import java.sql.SQLException;
import java.util.List;

import com.library_management.dao.implementation.BookDAOImplementation;
import com.library_management.dao.implementation.MemberDAOImplementation;
import com.library_management.domain.Book;
import com.library_management.domain.Member;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.IssueRecordServiceInterface;
import com.library_management.services.implementation.IssueRecordServiceImplementation;
import com.library_management.utilities.ResponseHandler;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ReturnBookController {

	IssueRecordServiceInterface service = new IssueRecordServiceImplementation();

	private List<Book> booksList;
	private List<Member> memberList;

	public void setBooksList(List<Book> booksList) {
		this.booksList = booksList;
	}

	public List<Book> getBooksList() {
		return this.booksList;
	}

	public void setMembersList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public List<Member> getMembersList() {
		return this.memberList;
	}

	@FXML
	private ComboBox<String> bookSelector;

	@FXML
	private ComboBox<String> memberSelector;

	@FXML
	private Text message;

	@FXML
	public void initialize() {
		try {
			List<Member> membersList = new MemberDAOImplementation().getAllMembers();
			this.setMembersList(membersList);
			for (Member member : membersList) {
				memberSelector.getItems().add(member.getMemberId() + ". " + member.getMemberName());
			}

			List<Book> booksList = new BookDAOImplementation().selectAllBooks();
			this.setBooksList(booksList);
			for (Book book : booksList) {
				bookSelector.getItems().add(book.getBookId() + ". " + book.getTitle());
			}
		} catch (DatabaseException | SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void loadBookDetails() {
		System.out.println("Book selected: " + bookSelector.getValue());
	}

	@FXML
	public void loadMemberDetails() {
		System.out.println("Member selected: " + memberSelector.getValue());
	}

	@FXML
	private void switchToReturnBookSubmit() {
		try {
			if (bookSelector.getValue() == null) {
				throw new InvalidDetailsException("Select Book To Return...");
			}
			if (memberSelector.getValue() == null) {
				throw new InvalidDetailsException("Select Member To Issue...");
			}
			int memberId = Integer.parseInt(memberSelector.getValue().split("\\.")[0].trim());

			int bookId = Integer.parseInt(bookSelector.getValue().split("\\.")[0].trim());

			String print_message = service.returnBook(memberId, bookId);
			System.out.println(print_message);
			if (print_message == "Book returned successfully") {
				ResponseHandler.showResponse(message, print_message, Color.GREEN);
			} else {
				ResponseHandler.showResponse(message, print_message, Color.RED);
			}
		} catch (InvalidDetailsException e) {
			ResponseHandler.showResponse(message, e.getMessage(), Color.RED);
		}
	}
}
