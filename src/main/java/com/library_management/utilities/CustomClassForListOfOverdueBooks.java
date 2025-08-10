package com.library_management.utilities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomClassForListOfOverdueBooks {

	private final SimpleIntegerProperty memberId;
	private final SimpleStringProperty memberName;
	private final SimpleIntegerProperty bookId;
	private final SimpleStringProperty bookTitle;
	private final SimpleStringProperty issueDate;

	public CustomClassForListOfOverdueBooks(String memberId, String memberName, String bookId, String bookTitle,
			String issueDate) {
		this.memberId = new SimpleIntegerProperty(Integer.parseInt(memberId));
		this.memberName = new SimpleStringProperty(memberName);
		this.bookId = new SimpleIntegerProperty(Integer.parseInt(bookId));
		this.bookTitle = new SimpleStringProperty(bookTitle);
		this.issueDate = new SimpleStringProperty(issueDate);

	}

	public int getMemberId() {
		return memberId.get();
	}

	public String getMemberName() {
		return memberName.get();
	}

	public int getBookId() {
		return bookId.get();
	}

	public String getBookTitle() {
		return bookTitle.get();
	}

	public String getIssueDate() {
		return issueDate.get();
	}

}
