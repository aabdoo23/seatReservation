package com.example.seatreservation;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;


import static com.example.seatreservation.globals.previewedUser;

public class NewUserController implements Initializable {
    @FXML
    public TextField tfID;
    @FXML
    public AnchorPane mainPanel;
    @FXML
    public TextField tfName;

    @FXML
    public TextField tfEmail;
    @FXML
    public TextField tfPN;
    @FXML
    public PasswordField pfPW1;
    @FXML
    public PasswordField pfPW2;
    @FXML
    public CheckBox cbCC;
    @FXML
    public TextField tfCN;
    @FXML
    public DatePicker dpExpDate;
    @FXML
    public TextField tfCHN;
    @FXML
    public PasswordField pfCvv;
    @FXML
    public Button saveButton;
    int id=globals.createNewRandomID(globals.usersIDs);
    public void save() {
        String email = tfEmail.getText();
        String first = tfName.getText();
        String pn = tfPN.getText();
        String pw = pfPW1.getText();
        if (tfEmail.getText().isEmpty() || tfName.getText().isEmpty() || tfPN.getText().isEmpty() || pw.isEmpty()) {

            globals.showErrorAlert("Error: Please fill all information");
            return;
        }
        if (!Objects.equals(pfPW1.getText(), pfPW2.getText())) {

            globals.showErrorAlert("Error: Passwords don't match");

            return;
        }
        if(cbCC.isSelected()){
            if (tfCN.getText().isEmpty() ||tfCN.getText().length()!=16|| tfCHN.getText().isEmpty() || pfCvv.getText().isEmpty()) {
                globals.showErrorAlert("Error: Please fill all card information and check your info");
                return;
            }
        }
        User user = new User(id, first, email, pw, pn);
        if(cbCC.isSelected()){
            CreditCard card=new CreditCard(globals.createNewRandomID(globals.ccIDs),tfCN.getText(),Integer.parseInt(pfCvv.getText()),dpExpDate.getValue(),tfCHN.getText());
            DB.addCreditCard(card);
            user.setCard(card.getID());
        }
        DB.addUser(user.getID(), user.getName(),user.getEmail(), user.getPassword(), user.getPhoneNumber(), user.getCard());

        globals.showConfirmationAlert("User registered");
        Stage stage=(Stage) tfID.getScene().getWindow();
        stage.close();
    }
    public void clickCB() {
        tfCHN.setEditable(cbCC.isSelected());
        tfCN.setEditable(cbCC.isSelected());
        pfCvv.setEditable(cbCC.isSelected());
        dpExpDate.setEditable(cbCC.isSelected());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (previewedUser==null) {
            tfID.setEditable(false);
            tfID.setText(Integer.toString(id));
            tfCHN.setEditable(cbCC.isSelected());
            tfCN.setEditable(cbCC.isSelected());
            pfCvv.setEditable(cbCC.isSelected());
            dpExpDate.setEditable(cbCC.isSelected());
            dpExpDate.setValue(LocalDate.now());
        }
        else {
            mainPanel.setDisable(true);
            CreditCard cc=DB.getCreditCardByUserID(previewedUser.getID());
            if(cc!=null){
                cbCC.setSelected(true);
                tfCN.setText(cc.getCardNumber());
                tfCHN.setText(cc.getHolderName());
                pfCvv.setText(Integer.toString(cc.getCVV()));
                dpExpDate.setValue(cc.getExpDate());
            }
            tfID.setText(Integer.toString(previewedUser.getID()));
            tfPN.setText(previewedUser.getPhoneNumber());
            tfName.setText(previewedUser.getName());
            tfEmail.setText(previewedUser.getEmail());
            pfPW1.setText(previewedUser.getPassword());
        }
    }
}
