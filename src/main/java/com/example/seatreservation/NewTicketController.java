package com.example.seatreservation;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class NewTicketController implements Initializable {

    public AnchorPane mainPanel;
    public TextField tfID;
    public AnchorPane moviePane;
    public Label lbMovieName;
    public ImageView ivPoster;
    public DatePicker dpDate;
    public ChoiceBox<String> cbParties;
    public Button selectBTN;
    public VBox seatsPane;
    public Spinner<Integer> numberOfSeatsSpinner;
    public Label lbMoney;
    public Button confirmBTN;
    Party selectedParty = null;
    int id = globals.createNewRandomID(globals.ticketsIDs);
    Movie movie = globals.movieForTicket;
    LinkedList<Party> parties = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Hall hall = null;
        if (selectedParty != null) hall = DB.getHallById(selectedParty.getHallID());
        updateDisplay();
        if (selectedParty != null) { //if there is a selected party from last window
            int cnt = 0; //cnt of available seats to set the maximum of choosing
            for (int i = 0; i < hall.getRows(); i++) { //all the rows of the hall
                for (int j = 0; j < hall.getColumns(); j++) { //all the column of the hall
                    if (!DB.getSeatByCoordinates(i, j, hall.getID()).isBooked()) cnt++;// if seat free inc counter
                }
            }
            numberOfSeatsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, cnt));//set the limit of the number of seats spinner
        }
        globals.spinnerTF(numberOfSeatsSpinner);//set number of seats properties
        tfID.setText(Integer.toString(id));//set tkt id
        lbMovieName.setText(movie.getMovieName());//movie name
        ivPoster.setImage(movie.getImg());//movie poster
        dpDate.setValue(LocalDate.now());//date picker for party day
        dpDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateDisplay();
        });
        dpDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }

                for (var i : DB.getPartiesByMovieID(movie.getID())) {
                    if (date.isEqual(i.getStartTime().toLocalDate())){
                        setStyle("-fx-background-color: lightgreen;");
                    }
                }
            }
        });

    }

    public void updateSeats() { //select party BTN

        if (selectedParty == null) return;//if fail in selected party return, just a safe exit

        seatsPane.getChildren().clear();//remove the current shown seats
        Hall hall = DB.getHallById(selectedParty.getHallID());
        for (int i = 0; i < hall.getRows(); i++) {//for all rows of this seating class
            HBox rowBox = new HBox();//make an hbox to store all buttons in row
            rowBox.setSpacing(5); //distance between buttons
            for (int j = 0; j < hall.getColumns(); j++) {//for number of columns
                int finalI = i;//the row location of the current button in respect to total seats
                int finalJ = j;//column location
                Button button = new Button();//button represents seat
                button.setText(Integer.toString(j + 1));// number of seats in row
                Seat seat = DB.getSeatByCoordinates(finalI, finalJ, hall.getID());
                button.setDisable(seat.isBooked());//if button previously booked disable it
                if (seat.isBooked())
                    button.setStyle("-fx-background-color: #11ff00;");//if button previously booked set color to green
                button.setOnAction(e -> {//when button clicked
                    if (button.getStyle().isEmpty()) {//if not green then not booked
                        if (selectSeat(seat, true))//if can be booked book it and mark green
                            button.setStyle("-fx-background-color: #11ff00;");
                    } else {
                        button.setStyle("");//else remove green and make it free
                        selectSeat(seat, false);
                    }
                });
                rowBox.getChildren().add(button);//add button to rowbox
            }
            seatsPane.getChildren().add(rowBox);//add rowbox to pane
        }
        seatsPane.setSpacing(5);//pane spacing

    }

    int seatCounter = 0;//current seats booked counter
    LinkedList<Seat> pickedSeats = new LinkedList<>();//final picked seats

    public boolean selectSeat(Seat seat, boolean f) { //seat function
        if (seatCounter >= numberOfSeatsSpinner.getValue() && f) {//if user wants to book more seats than marked show error and abort
            globals.showErrorAlert("Error: You have already chosen " + numberOfSeatsSpinner.getValue() + " seats");
            return false;
        }
        if (f) {
            seatCounter++; //if bookable increase booked seats and increase total money of tickets
            lbMoney.setText(Double.toString(Integer.parseInt(lbMoney.getText()) + seat.getSeatPrice()));
        } else {
            seatCounter--;//else was already booked, so unbook
            lbMoney.setText(Double.toString(Integer.parseInt(lbMoney.getText()) - seat.getSeatPrice()));

        }
        seat.setBooked(f);//set booked status as entered
        for (Seat seat1 : pickedSeats) {//check if seat already added to picked seats
            if (seat1.getID() == seat.getID()) {
                seat1.setBooked(f);//change the preexistent status of seat and abort
                return true;
            }
        }
        pickedSeats.add(seat);//add to picked seats
        return true;
    }

    public void updateDisplay() { //selectBTN //dp
        if (selectedParty != null) { //calculate number of tickets to choose from
//            System.out.println("a");
            int cnt = 0;
            Hall hall = DB.getHallById(selectedParty.getHallID());
            for (int i = 0; i < hall.getRows(); i++) {
                for (int j = 0; j < hall.getColumns(); j++) {
                    if (!DB.getSeatByCoordinates(i, j, hall.getID()).isBooked()) cnt++;
                }
            }
            numberOfSeatsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, cnt));
        }



        parties = new LinkedList<>(); // build content of cbParties
        LocalDate date = dpDate.getValue();
        for (Party party : DB.getPartiesByMovieID(movie.getID())) {
            System.out.println(party.getStartTime().toLocalDate().toString() + " " + date);
            if (date == null) {
                parties.add(party);
            } else if (party.getStartTime().toLocalDate().toString().equals(date.toString())) {
                parties.add(party);
            }
        }

        if (parties.size() != 0) {
            globals.makeList(parties, cbParties);
            cbParties.getSelectionModel().selectFirst();
        } else {
            globals.showErrorAlert("No available parties for this date.");
        }
    }

    public void chooseParty() { //selectBTN
        seatCounter = 0;
        pickedSeats = new LinkedList<>();
        if (cbParties.getSelectionModel().getSelectedIndex() == -1 || cbParties.getSelectionModel().isEmpty()) {

            globals.showErrorAlert("Selection invalid");
            return;
        }
        selectedParty = parties.get(cbParties.getSelectionModel().getSelectedIndex());
        updateSeats();
        updateDisplay();
        lbMoney.setText(Integer.toString(0));
    }

    public void save() throws IOException {//confirmBTN
        globals.currentPendingTKTs.clear();

        for (Seat seat : pickedSeats) {
            DB.updateSeat(seat);

            Ticket ticket = new Ticket(globals.createNewRandomID(globals.ticketsIDs), LocalDateTime.now(), globals.signedInUser.getID(), selectedParty.getID(), seat.getID());
            DB.addTicket(ticket);
            globals.currentPendingTKTs.add(ticket);
        }
        globals.openNewForm("checkoutTicket.fxml", "Payment");
        Stage s = (Stage) tfID.getParent().getScene().getWindow();
        s.close();
    }

}
