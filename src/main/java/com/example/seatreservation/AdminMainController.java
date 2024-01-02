package com.example.seatreservation;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {
    public Button newHallBTN;
    public Button newPartyBTN;
    public Button newMovieBTN;
    public Button refreshBTN;
    public ListView<Hall> hallsList;
    public ListView<Movie> moviesList;
    public ListView<Party> partiesList;
    public ListView<User> usersList;
    public Button newUserBTN;
    public Button previewHallBTN;
    public Button previewMovieBTN;
    public Button previewPartyBTN;
    public Button previewUserBTN;
    public ListView<Ticket> ticketsList;
    public Button previewTKTbtn;

    public void updateDisplay(){
        globals.makeList(DB.getAllHalls(),hallsList);//all halls
        globals.makeList(DB.getAllParties(),partiesList);//all parties
        globals.makeList(DB.getAllMovies(),moviesList);//all movies
        globals.makeList(DB.getAllUsers(),usersList);//all users
        globals.makeList(DB.getAllTickets(),ticketsList);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateDisplay();
    }
    public void newHALL() throws IOException {
        globals.openNewForm("newHall.fxml","New hall");//open new hall form
        updateDisplay();
    }
    public void newMOVIE() throws IOException {
        globals.openNewForm("newMovie.fxml","New movie");//open new movie form
        updateDisplay();
    }
    public void newParty() throws IOException {
        globals.openNewForm("newParty.fxml","New party");//open new party form
        updateDisplay();
    }
    public void newUser() throws IOException {
        globals.openNewForm("newUserView.fxml","New user");//open new user form
        updateDisplay();
    }
    public void prevTKT() throws IOException {
        globals.currentViewTKT =DB.getTicketByID(ticketsList.getSelectionModel().getSelectedItem().getID());
        globals.openNewForm("checkoutTicket.fxml","View ticket");//open new user form
        globals.currentViewTKT =null;
    }
    public void prevUser() throws IOException {
        globals.previewedUser=DB.getUserByID(usersList.getSelectionModel().getSelectedItem().getID());
        globals.openNewForm("newUserView.fxml","View user");
        globals.previewedUser=null;
    }
    public void prevHall() throws IOException {
        globals.prevHall=DB.getHallById(hallsList.getSelectionModel().getSelectedItem().getID());
        globals.openNewForm("newHall.fxml","View Hall");
        globals.prevHall=null;
    }
    public void prevMovie() throws IOException {
        globals.previewedMovie=DB.getMovieByID(moviesList.getSelectionModel().getSelectedItem().getID());
        globals.openNewForm("newMovie.fxml","View Movie");
        globals.previewedMovie=null;
    }
    public void prevParty() throws IOException {
        globals.prevParty=DB.getPartyByID(partiesList.getSelectionModel().getSelectedItem().getID());
        globals.openNewForm("newParty.fxml","View party");
        globals.prevParty=null;
    }
}
