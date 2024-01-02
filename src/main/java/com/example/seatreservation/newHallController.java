package com.example.seatreservation;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.seatreservation.globals.prevHall;
import static com.example.seatreservation.globals.spinnerTF;


public class newHallController implements Initializable {

    public TextField tfID;
    public TextField rowsSpinner;
    public Spinner<Integer> columnSpinner;
    public TextField tfName;
    public Spinner<Integer> FCNspinner;
    public Spinner<Integer>tfFCP;
    public Spinner<Integer> SCNspinner;
    public Spinner<Integer>tfSCP;
    public Spinner<Integer> TCNspinner;
    public Spinner<Integer> tfTCP;

    public AnchorPane mainPanel;
    public Button saveButton;
    public TextField tfSeats;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(prevHall==null) {
            tfID.setEditable(false);
            tfSeats.setEditable(false);
            int id = globals.createNewSeqID(globals.hallsIDs);
            tfID.setText(Integer.toString(id));
            tfName.setText("Hall " + id);
            mainPanel.setMaxSize(mainPanel.getPrefHeight(), mainPanel.getPrefWidth());
            columnSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30));
            SCNspinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30));
            FCNspinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30));
            TCNspinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30));
            tfSCP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
            tfFCP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));
            tfTCP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000));

            spinnerTF(tfTCP);
            spinnerTF(tfSCP);
            spinnerTF(tfFCP);
            spinnerTF(columnSpinner);
            spinnerTF(TCNspinner);
            spinnerTF(FCNspinner);
            spinnerTF(SCNspinner);


            initDisplay();

            columnSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                initDisplay();
            });
            FCNspinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                initDisplay();
            });
            SCNspinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                initDisplay();
            });
            TCNspinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                initDisplay();
            });
        }
        else{
            mainPanel.setDisable(true);
            tfID.setText(Integer.toString(prevHall.getID()));
            tfName.setText(prevHall.getName());
            columnSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(prevHall.getColumns(), prevHall.getColumns()));
            rowsSpinner.setText(Integer.toString(prevHall.getRows()));
            tfSeats.setText(Integer.toString(prevHall.getColumns()*prevHall.getRows()));
        }
    }


    public void initDisplay(){
        rowsSpinner.setText(Integer.toString(FCNspinner.getValue()+ SCNspinner.getValue()+TCNspinner.getValue()));
        tfSeats.setText(Integer.toString(Integer.parseInt(rowsSpinner.getText())*columnSpinner.getValue()));
    }

    public void saveButtonC(){
        int id=Integer.parseInt(tfID.getText());
        int nr= Integer.parseInt(rowsSpinner.getText());
        int nc= columnSpinner.getValue();
        String hn=tfName.getText();
        int fr= FCNspinner.getValue(),sr=SCNspinner.getValue(),tr=TCNspinner.getValue();
        int fp=tfFCP.getValue(),sp=tfSCP.getValue(),tp=tfTCP.getValue();
        Hall hall=new Hall(id,hn,nr,nc);
        DB.addHall(hall);

        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                int ide=Integer.parseInt(id +String.valueOf(i)+ j);
                double p;
                if(i<fr+sr&&i>=fr)p=sp;
                else if(i<fr+sr+tr&&i>=fr+sr)p=tp;
                else p=fp;
                Seat seat=new Seat(ide,false,i,j,p,id);
                DB.addSeat(seat);

            }
        }

        globals.showConfirmationAlert("Hall registered");
        Stage stage=(Stage) mainPanel.getScene().getWindow();
        stage.close();
    }
}
