package com.example.seatreservation;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable {
    public AnchorPane mainPanel;
    public TextField tfID;
    public Button loginButton;
    public PasswordField pfPW;
    public Button signUpButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void login() throws IOException {

        if(tfID.getText().isEmpty()||pfPW.getText().isEmpty()){
            globals.showErrorAlert("Empty fields");
            return;
        }
        int id=Integer.parseInt(tfID.getText());
        String pw=pfPW.getText();
        if(id==1&& Objects.equals(pw, "1")){
            globals.openNewForm("adminMain.fxml","Admin");
            return;
        }
        else if(id==2&& Objects.equals(pw, "2")){
            globals.openNewForm("userMain.fxml","User");
        }
        LinkedList<User>allUsers=DB.getAllUsers();
        for (User user:allUsers){
            if (user.getID()==id){
                if(Objects.equals(user.getPassword(), pw)){
                    globals.signedInUser=user;
                    globals.openNewForm("userMain.fxml","User");
                    tfID.setText("");
                }
                else{
                    globals.showErrorAlert("Invalid password");
                }
                pfPW.setText("");
                return;
            }
        }
        globals.showErrorAlert("Invalid credentials");
        tfID.setText("");
        pfPW.setText("");
    }



    public void open() throws IOException {
        globals.openNewForm("newUserView.fxml","New user");
    }
}
