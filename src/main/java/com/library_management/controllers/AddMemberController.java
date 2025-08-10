package com.library_management.controllers;

import java.io.IOException;

import com.library_management.App;
import com.library_management.constants.MemberGender;
import com.library_management.domain.Member;
import com.library_management.services.implementation.MemberServiceImplementation;
import com.library_management.utilities.ResponseHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AddMemberController {

	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField mobile;
	@FXML
	private ComboBox<MemberGender> genderComboBox; // Use enum instead of String
	@FXML
	private TextField address;
	@FXML
	private Button register;
	@FXML
	private Text message;

	private MemberServiceImplementation memberService = new MemberServiceImplementation();

	@FXML
	private void backButton() throws IOException {
		App.setRoot("memberOptions");
	}

	@FXML
	private void homeButton() throws IOException {
		App.setRoot("home");
	}

	@FXML
	public void initialize() {

		name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[a-zA-Z ]*")) {
				name.setText(oldValue);
			}
		});

		mobile.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*") || newValue.length() > 10) {
				mobile.setText(oldValue);
			}
		});

		genderComboBox.getItems().addAll(MemberGender.values());

		register.setOnAction(e -> {
			String memberName = name.getText();
			String memberMail = email.getText();
			String mobileNo = mobile.getText();
			MemberGender gender = genderComboBox.getSelectionModel().getSelectedItem();
			String memberAddress = address.getText();

			Member member = new Member(memberName, memberMail, mobileNo, gender, memberAddress);

			try {
				memberService.addMember(member);
				ResponseHandler.showResponse(message, "Member Added Successfully...", Color.GREEN);
				clearForm();
			} catch (Exception ex) {
				ResponseHandler.showResponse(message, ex.getMessage(), Color.RED);
			}
		});
	}

	private void clearForm() {
		name.clear();
		email.clear();
		mobile.clear();
		genderComboBox.getSelectionModel().clearSelection();
		address.clear();
	}
}
