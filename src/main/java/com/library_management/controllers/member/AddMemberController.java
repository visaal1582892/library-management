package com.library_management.controllers.member;
import com.library_management.utilities.ResponseHandler;
import com.library_management.domain.Member;
import com.library_management.services.implementation.MemberServiceImplementation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AddMemberController {

    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField mobile;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField address;
    @FXML private Button register;
    @FXML private Text message;

    private MemberServiceImplementation memberService = new MemberServiceImplementation();
    @FXML
    public void initialize() {
        genderComboBox.getItems().addAll("Male", "Female");

        register.setOnAction(e -> {
            String memberName=name.getText();
            String memberMail=email.getText();
            String mobileNo=mobile.getText();
            String gender=genderComboBox.getValue();
            String memberAddress=address.getText();

            Member member=new Member(0,memberName,memberMail,mobileNo,gender,memberAddress);

            try{
                memberService.addMember(member);
                ResponseHandler.showResponse(message, "Book Added Succesfully...", Color.GREEN);
                clearForm();
            }
            catch(Exception ex) {
                showAlert("Error",ex.getMessage(),Alert.AlertType.ERROR);
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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
