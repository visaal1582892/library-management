package com.library_management.controllers.member;

import java.sql.SQLException;
import java.util.List;

import com.library_management.dao.implementation.MemberDAOImplementation;
import com.library_management.domain.Member;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UpdateMemberDetailsController {
	
	private List<Member> memberList;
	
	public void setMembersList(List<Member> memberList) {
		this.memberList=memberList;
	}
	
	public List<Member> getmembersList(){
		return this.memberList;
	}
	
	@FXML
	private ComboBox<String> memberSelector;
	
	@FXML
	private TextField memberNameField;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField mobileNoField;
	
	@FXML
	private ComboBox<String> genderComboBox;
	
	@FXML
	private TextField addressField;
	
	@FXML
    public void initialize() throws SQLException, DatabaseException {
		List<Member> membersList=new MemberDAOImplementation().getAllMembers();
		this.setMembersList(membersList);
		for(Member member:membersList) {
			memberSelector.getItems().add(member.getMemberId()+":"+member.getMemberName());
		}
		
		genderComboBox.getItems().addAll("Male","Female");
    	
    }

	
	@FXML
	public void loadMemberDetails() 
	{
		int id=Integer.parseInt(memberSelector.getValue().split("\\:")[0].trim());
		Member selectedMember=this.getmembersList().stream()
				.filter(b -> b.getMemberId()==id)
				.findFirst()
				.orElse(null);
		if(selectedMember!=null) {
			memberNameField.setText(selectedMember.getMemberName());
			emailField.setText(selectedMember.getMemberMail());
			mobileNoField.setText(selectedMember.getMobileNo());
			genderComboBox.setValue(selectedMember.getGender());
			addressField.setText(selectedMember.getMemberAddress());
		}
	}
	
	
}
