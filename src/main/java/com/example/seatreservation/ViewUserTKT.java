package com.example.seatreservation;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ViewUserTKT implements Initializable {

    public ListView<String>  lvTickets;
    public User user = globals.signedInUser;


    public void updateDisplay(){
        LinkedList<Ticket>curr=new LinkedList<>();
        LinkedList<Ticket>allTickets=DB.getAllTickets();
        for (Ticket tkt:allTickets){
            if (tkt.getUserId()==user.getID()){
                curr.add(tkt);
            }
        }
        globals.makeList(curr,lvTickets);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateDisplay();
    }
}
