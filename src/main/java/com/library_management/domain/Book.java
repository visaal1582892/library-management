package com.library_management.domain;

public class Book {
	// All fields of a book
		private int bookId;
		private String title;
		private String author;
		private String category;
		private String status;
		private String availability;
		
//		Constructors
		public Book(String title, String author, String category) {
			this(title,author,category,"A","A");
		}
		
		public Book(String title, String author, String category, String status, String availability) {
			this.title=title;
			this.author=author;
			this.category=category;
			this.status=status;
			this.availability=availability;
		}

		public int getBookId() {
			return bookId;
		}

		public void setBookId(int bookId) {
			this.bookId = bookId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getAvailability() {
			return availability;
		}

		public void setAvailability(String availability) {
			this.availability = availability;
		}
		
		
		
}
