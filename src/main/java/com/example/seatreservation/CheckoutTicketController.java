package com.example.seatreservation;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class CheckoutTicketController implements Initializable {
    public TextField tfIssueTime;
    public TextField tfID;
    public TextField tfName;
    public ImageView ivPoster;
    public TextField tfMovieName;
    public TextField tfHallName;
    public TextField tfDateTime;
    public Button printTKTBTN;
    public RadioButton rbCash;
    public RadioButton rbCC;
    public TextField tfCN;
    public DatePicker dpExpDate;
    public TextField tfCHN;
    public PasswordField pfCvv;
    public CheckBox cbUseCC;
    public CheckBox cbRegisterAsCC;
    public Button saveBTN;
    public AnchorPane mainPanel;
    public AnchorPane apCC;
    public TextField tfSeatsBooked;
    public TextField tfPrice;
    Ticket ticket=new Ticket();

    double collectedPrice=0;
    LinkedList<Seat> bookedSeats=new LinkedList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for( var t : globals.currentPendingTKTs){
            collectedPrice+=t.getPrice();
            bookedSeats.add(DB.getSeatById(ticket.getSeatId()));
        }
        User user=globals.signedInUser;
        ticket=globals.currentPendingTKTs.get(0); //assign ticket to work on as the chosen tkt
        tfID.setText(Integer.toString(ticket.getID()));//set tkt id
        tfIssueTime.setText(ticket.getIssueTime().toString());//set issue time as now
        tfName.setText(user.getName());//set username
        tfHallName.setText(DB.getHallByTicketId(ticket.getID()).getName()); //set hall name
        tfDateTime.setText(DB.getPartyByTicketId(ticket.getID()).getStartTime().toString()); //set party time
        rbCash.selectedProperty().setValue(true);//select cash option first
        apCC.setDisable(true);//disable cc part
        tfPrice.setText(Double.toString(collectedPrice));//set tkt price
        tfSeatsBooked.setText(bookedSeats.toString());//seats chosen
        dpExpDate.setValue(LocalDate.now()); //date preset to now
        ToggleGroup tg=new ToggleGroup();
        rbCC.setToggleGroup(tg);
        rbCash.setToggleGroup(tg);
        rbCash.setOnAction(e->{//if clicked on rbCash
            apCC.setDisable(rbCash.isSelected());//if selected cash disable cc part
        });
        rbCC.setOnAction(e -> {//if clicked on rbCC
            apCC.setDisable(rbCash.isSelected());
        });
        CreditCard cc=DB.getCreditCardByUserID(user.getID());
        if(cc==null){//if user has no card
            cbUseCC.setDisable(true);//disable cc part
        }
        Movie movie=DB.getMovieByTicketId(ticket.getID());
        tfMovieName.setText(movie.getMovieName());//movie name
        ivPoster.setImage(movie.getImg());//movie poster
        cbUseCC.setOnAction(event -> {//if clicked use user cc cb
            tfCN.setDisable(cbUseCC.isSelected());//disable taking info if selected and enable if not
            tfCHN.setDisable(cbUseCC.isSelected());//disable taking info if selected and enable if not
            dpExpDate.setDisable(cbUseCC.isSelected());//disable taking info if selected and enable if not
            pfCvv.setDisable(cbUseCC.isSelected());//disable taking info if selected and enable if not
            if(cbUseCC.isSelected()) {//if user wants to use their cc
                tfCN.setText(cc.getCardNumber());//get info of user cc
                tfCHN.setText(cc.getHolderName());//get info of user cc
                pfCvv.setText(Integer.toString(cc.getCVV()));//get info of user cc
                dpExpDate.setValue(cc.getExpDate());//get info of user cc
            }

        });

    }
    public void save(){
        if(rbCC.isSelected()&&(tfCN.getText().isEmpty()||tfCHN.getText().isEmpty()||pfCvv.getText().isEmpty())){//if user selected cc and cc data empty
            globals.showErrorAlert("Billing information missing");//error and return
            return;
        }
//        for(var t : globals.currentPendingTKTs){
//            DB.addTicket(t);//add ticket to db
//        }
        if(cbRegisterAsCC.isSelected()){
            int id=globals.createNewRandomID(globals.ccIDs);
            User user=globals.signedInUser;
            user.setCard(id);
            DB.editUserByID(user.getID(),user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(), id);
            DB.addCreditCard(new CreditCard(id,tfCN.getText(),Integer.parseInt(pfCvv.getText()),dpExpDate.getValue(),tfCHN.getText()));
        }
        globals.showConfirmationAlert("Ticket booked");
//        Stage s=(Stage)tfID.getParent().getScene().getWindow();
//        s.close();
    }
    public void printTKT(){
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintTicket printable = new PrintTicket(ticket);
        job.setPrintable(printable);
        if (job.printDialog()) {
            try {
                job.print();
                globals.showConfirmationAlert("Print successful");
            } catch (PrinterException x) {
                globals.showErrorAlert("Printing ERROR");
                x.printStackTrace();
            }
        }
    }
}
