package com.library_management.utilities;

import com.library_management.constants.MemberGender;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemberTableView {
	private final SimpleIntegerProperty memberId;
	private final SimpleStringProperty name;
	private final SimpleStringProperty emailId;
	private final SimpleStringProperty mobileNo;
	private final SimpleObjectProperty<MemberGender> gender;
	private final SimpleStringProperty address;

	public MemberTableView(int id, String name, String emailId, String mobileNo, MemberGender gender, String address) {
		this.memberId = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.emailId = new SimpleStringProperty(emailId);
		this.mobileNo = new SimpleStringProperty(mobileNo);
		this.gender = new SimpleObjectProperty<>(gender);
		this.address = new SimpleStringProperty(address);
	}

	// Getters for values
	public int getMemberId() {
		return memberId.get();
	}

	public String getName() {
		return name.get();
	}

	public String getEmailId() {
		return emailId.get();
	}

	public String getMobileNo() {
		return mobileNo.get();
	}

	public MemberGender getGender() {
		return gender.get();
	}

	public String getAddress() {
		return address.get();
	}

	// Property getters for table binding
	public SimpleIntegerProperty memberIdProperty() {
		return memberId;
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public SimpleStringProperty emailIdProperty() {
		return emailId;
	}

	public SimpleStringProperty mobileNoProperty() {
		return mobileNo;
	}

	public SimpleObjectProperty<MemberGender> genderProperty() {
		return gender;
	}

	public SimpleStringProperty addressProperty() {
		return address;
	}
}
