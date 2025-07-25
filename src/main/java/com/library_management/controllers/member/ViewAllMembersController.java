package com.library_management.controllers.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.library_management.App;
import com.library_management.domain.Book;
import com.library_management.domain.Member;
import com.library_management.exceptions.DatabaseException;
import com.library_management.services.implementation.BookServiceImplementation;
import com.library_management.services.implementation.MemberServiceImplementation;
import com.library_management.utilities.MemberTableView;
import com.library_management.utilities.ResponseHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ViewAllMembersController {
	@FXML
    private TableView<MemberTableView> viewMembersTable;
    @FXML
    private TableColumn<MemberTableView, Integer> memberIdColumn;
    @FXML
    private TableColumn<MemberTableView, String> memberNameColumn;
    @FXML
    private TableColumn<MemberTableView, String> memberMailColumn;
    @FXML
    private TableColumn<MemberTableView, String> memberMobileNoColumn;
    @FXML
    private TableColumn<MemberTableView, String> memberGenderColumn;
    @FXML
    private TableColumn<MemberTableView, String> memberAddressColumn;
    @FXML
    private Text message;
    
    @FXML
    private void backButton() throws IOException {
        App.setRoot("memberOptions");
    }
    
    @FXML
	private void homeButton() throws IOException{
		App.setRoot("home");
    }
//    Creating Observable list
    private ObservableList<MemberTableView> memberData = FXCollections.observableArrayList();
	
	@FXML
    public void initialize() throws SQLException {
		
//		Creating cell value factories for AllPermission columns
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberMailColumn.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        memberMobileNoColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
        memberGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        memberAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
			List<Member> members=new MemberServiceImplementation().validateViewAllMembers();
			for(Member member:members) {
				memberData.add(new MemberTableView(member.getMemberId(),member.getMemberName(), member.getMemberMail(), member.getMobileNo(),member.getGender(), member.getMemberAddress()));
			}
		} catch (DatabaseException e) {
			ResponseHandler.showResponse(message, "Cannot Fetch Members Data...", Color.RED);
		}
		
        viewMembersTable.setItems(memberData);
		ResponseHandler.showResponse(message, "Members Fetched Succesfully...", null);
    }
}
