package com.library_management.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.library_management.App;
import com.library_management.constants.BookCategory;
import com.library_management.dao.implementation.BookDaoImplementation;
import com.library_management.domain.Book;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.implementation.BookServiceImplementation;
import com.library_management.utilities.ResponseHandler;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class UpdateBookDetailsController {

    private List<Book> booksList;
    private Book selectedBook;

    public void clearForm() {
        searchField.clear();
        suggestionsList.setVisible(false);
        titleField.setText("");
        authorField.setText("");
        categoryComboBox.setValue(null);
        selectedBook = null;
        message.setText("");
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }

    public List<Book> getBooksList() {
        return this.booksList;
    }

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> suggestionsList;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private ComboBox<BookCategory> categoryComboBox;

    @FXML
    private Text message;

    @FXML
    private void backButton() throws IOException {
        App.setRoot("bookOptions");
    }

    @FXML
    private void homeButton() throws IOException {
        App.setRoot("home");
    }

    @FXML
    public void initialize() {
        // Validation for Title
        titleField.textProperty().addListener((obs, oldValue, newValue) -> {
            String cleaned = newValue.replaceAll("[^a-zA-Z0-9 ]", "");
            if (cleaned.length() > 60) {
				cleaned = cleaned.substring(0, 60);
			}
            if (!cleaned.equals(newValue)) {
				titleField.setText(cleaned);
			}
        });

        // Validation for Author
        authorField.textProperty().addListener((obs, oldValue, newValue) -> {
            String cleaned = newValue.replaceAll("[^a-zA-Z ]", "");
            if (cleaned.length() > 60) {
				cleaned = cleaned.substring(0, 60);
			}
            if (!cleaned.equals(newValue)) {
				authorField.setText(cleaned);
			}
        });

        try {
            List<Book> booksList = new BookDaoImplementation().selectAllBooks();
            this.setBooksList(booksList);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        categoryComboBox.getItems().addAll(BookCategory.values());

        // Hide suggestions initially
        suggestionsList.setVisible(false);

        // Handle suggestion click
        suggestionsList.setOnMouseClicked(event -> {
            String selected = suggestionsList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                loadBookDetails(selected);
                suggestionsList.setVisible(false);
            }
        });

        // Live search
        searchField.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            String query = searchField.getText().toLowerCase().trim();
            if (query.isEmpty()) {
                suggestionsList.getItems().clear();
                suggestionsList.setVisible(false);
                return;
            }
            List<String> matched = booksList.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(query)
                            || book.getAuthor().toLowerCase().contains(query))
                    .map(book -> book.getBookId() + ". " + book.getTitle() + " - " + book.getAuthor())
                    .collect(Collectors.toList());

            suggestionsList.getItems().setAll(matched);
            suggestionsList.setVisible(!matched.isEmpty());
        });
    }

    private void loadBookDetails(String selectedValue) {
        int id = Integer.parseInt(selectedValue.split("\\.")[0].trim());
        selectedBook = this.getBooksList().stream()
                .filter(b -> b.getBookId() == id)
                .findFirst()
                .orElse(null);
        if (selectedBook != null) {
            titleField.setText(selectedBook.getTitle());
            authorField.setText(selectedBook.getAuthor());
            categoryComboBox.setValue(selectedBook.getCategory());
        }
    }

    @FXML
    public void handleButtonClick() {
        try {
            if (selectedBook == null) {
                throw new InvalidDetailsException("First search and select a book to update...");
            }
            String title = titleField.getText();
            String author = authorField.getText();
            BookCategory category = categoryComboBox.getSelectionModel().getSelectedItem();

            new BookServiceImplementation().validateUpdateBookDetails(selectedBook.getBookId(), title, author, category);

            App.setRoot("updateBookDetails");

            clearForm();
            ResponseHandler.showResponse(message, "Book Details Updated Successfully...", Color.GREEN);
        } catch (InvalidDetailsException | DatabaseException | IOException e) {
            ResponseHandler.showResponse(message, e.getMessage(), Color.RED);
        }
    }
}
