package com.example.seatreservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class globals {
    public static Ticket prevTKT;
    public static User previewedUser;
    public static Movie previewedMovie;
    public static Party prevParty;
    public static Hall prevHall;
    public static User signedInUser;
    public static Movie movieForTicket;
    public static LinkedList<Ticket> currentPendingTKTs=new LinkedList<>();


    public static boolean[] ccIDs = new boolean[2000];
    public static boolean[] usersIDs = new boolean[2000];
    public static boolean[] ticketsIDs = new boolean[2000];
    public static boolean[] hallsIDs = new boolean[2000];
    public static boolean[] partiesIDs = new boolean[2000];
    public static boolean[] moviesIDs = new boolean[2000];
    public static Ticket currentViewTKT;

    static boolean marked=false;
    private static void markIDs(){
        if(marked)return;
        marked=true;
        for(var i : DB.getAllMovies())moviesIDs[i.getID()]=true;
        for(var i : DB.getAllHalls())hallsIDs[i.getID()]=true;
        for(var i : DB.getAllUsers())usersIDs[i.getID()]=true;
        for(var i : DB.getAllParties())partiesIDs[i.getID()]=true;
        for(var i : DB.getAllTickets())ticketsIDs[i.getID()]=true;
    }
    public static int createNewRandomID(boolean[] v) {
        markIDs();
        Random random = new Random();
        int x = random.nextInt(v.length-1);
        while (v[x]) {
            x = random.nextInt(v.length-1);
        }
        v[x] = true;
        return x;
    }
    public static int createNewSeqID(boolean[] v) {
        markIDs();

        for (int i=1;i<v.length;i++){
            if(!v[i]){
                v[i]=true;
                return i;
            }
        }
        return 0;
    }
    public static void showErrorAlert(String content){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void showConfirmationAlert(String content){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmed");
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static void openNewForm(String formName,String title) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(globals.class.getResource(formName)));
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    public static void spinnerTF(Spinner<Integer> spinner) {
        spinner.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            } else {
                return null;
            }
        }));
    }

    public static ObservableList<String> makeObsList(LinkedList linkedList) {
        String[] strings = new String[linkedList.size()];
        int i = 0;
        for (Object ob : linkedList) {
            strings[i] = ob.toString();
            i++;
        }
        return FXCollections.observableArrayList(strings);
    }

    public static void makeList(LinkedList linkedList, ListView list) {
        ObservableList obs= FXCollections.observableArrayList(linkedList);
        list.setItems(obs);
    }

    public static void makeList(LinkedList linkedList, ChoiceBox list) {
        ObservableList obs= FXCollections.observableArrayList(linkedList);
        list.setItems(obs);
    }
    public static void defaultMakeList(LinkedList linkedList, ComboBox<String> list) {
        String[] strings = new String[linkedList.size()+1];
        strings[0]="-";
        int i = 1;
        for (Object ob : linkedList) {
            strings[i] = ob.toString();
            i++;
        }
        ObservableList<String>obs= FXCollections.observableArrayList(strings);
        list.setItems(obs);
    }

}
