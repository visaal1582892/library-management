package com.library_management.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.library_management.App;
import com.library_management.constants.MemberGender;
import com.library_management.dao.implementation.MemberDaoImplementation;
import com.library_management.domain.Member;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.implementation.MemberServiceImplementation;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UpdateMemberDetailsController {

	private List<Member> memberList;
	private Member selectedMember;

	public void setMembersList(List<Member> membersList) {
		this.memberList = membersList;
	}

	public List<Member> getMembersList() {
		return this.memberList;
	}

	@FXML
	private void backButton() throws IOException {
		App.setRoot("memberOptions");
	}

	@FXML
	private void homeButton() throws IOException {
		App.setRoot("home");
	}

	@FXML
	private TextField searchField;

	@FXML
	private ListView<String> suggestionList;

	@FXML
	private TextField nameField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField mobileNoField;

	@FXML
	private ComboBox<MemberGender> genderComboBox;

	@FXML
	private TextField addressField;

	@FXML
	private Text message;

	@FXML
	public void initialize() throws SQLException, DatabaseException {
		List<Member> membersList = new MemberDaoImplementation().getAllMembers();
		this.setMembersList(membersList);

		nameField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z ]*")) {
				nameField.setText(oldValue);
			}
		});

		mobileNoField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*") || newValue.length() > 10) {
				mobileNoField.setText(oldValue);
			}
		});

		genderComboBox.getItems().addAll(MemberGender.values());

		searchField.setOnKeyReleased(e -> {
			String query = searchField.getText().toLowerCase();
			if (query.isEmpty()) {
				suggestionList.setVisible(false);
				return;
			}

			List<String> suggestions = memberList.stream().filter(m -> m.getMemberName().toLowerCase().contains(query))
					.map(Member::getMemberName).collect(Collectors.toList());

			if (suggestions.isEmpty()) {
				suggestionList.setVisible(false);
			} else {
				suggestionList.getItems().setAll(suggestions);
				suggestionList.setVisible(true);
			}
		});

		suggestionList.setOnMouseClicked(e -> {
			String name = suggestionList.getSelectionModel().getSelectedItem();
			if (name != null) {
				loadMemberDetailsByName(name);
				suggestionList.setVisible(false);
			}
		});
	}

	private void loadMemberDetailsByName(String name) {
		selectedMember = memberList.stream().filter(m -> m.getMemberName().equalsIgnoreCase(name)).findFirst()
				.orElse(null);

		if (selectedMember != null) {
			nameField.setText(selectedMember.getMemberName());
			emailField.setText(selectedMember.getMemberMail());
			mobileNoField.setText(selectedMember.getMobileNo());
			genderComboBox.setValue(selectedMember.getGender());
			addressField.setText(selectedMember.getMemberAddress());
			message.setText("");
		}
	}

	@FXML
	public void handleButtonClick() {
		if (selectedMember == null) {
			message.setText("Please search and select a member first.");
			message.setFill(Color.RED);
			return;
		}

		try {
			Member updatedMember = new Member();
			updatedMember.setMemberId(selectedMember.getMemberId());
			updatedMember.setMemberName(nameField.getText());
			updatedMember.setMemberMail(emailField.getText());
			updatedMember.setMobileNo(mobileNoField.getText());
			updatedMember.setGender(genderComboBox.getValue());
			updatedMember.setMemberAddress(addressField.getText());

			new MemberServiceImplementation().validateUpdateMemberDetails(updatedMember);

			message.setText("Member updated successfully.");
			message.setFill(Color.GREEN);

		} catch (InvalidDetailsException | DatabaseException | SQLException e) {
			message.setText(e.getMessage());
			message.setFill(Color.RED);
		} catch (Exception e) {
			message.setText("Unexpected error occurred.");
			message.setFill(Color.RED);
		}

		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> message.setText(""));
		pause.play();
	}
}
