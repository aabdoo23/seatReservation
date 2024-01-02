package com.example.seatreservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class UserMainController implements Initializable {
    Stage ps;
    @FXML
    public TableView<Movie> tvMoviesTable;
    @FXML
    public TableColumn<Movie, Image> posterCol;
    @FXML
    public TableColumn<Movie, String> nameCol;
    public Button bookButton;
    public Button viewTKTbutton;
    ObservableList<Movie>movies= FXCollections.observableArrayList();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        posterCol.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);

                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitWidth(80);
                    imageView.setFitHeight(120);
                    setGraphic(imageView);
                }
            }
        });
        posterCol.setCellValueFactory(new PropertyValueFactory<>("img"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("movieName"));
//        ObservableList<String>movies=globals.makeObsList(globals.moviesLinkedList);
        tvMoviesTable.setItems(getMoviesList());
//        tfMovieSearch.textProperty().addListener((observable, oldValue, newValue) -> {
//            String filter = newValue.toLowerCase();
//            tvMoviesTable.setItems(movies.filtered(movie -> movie.getMovieName().toLowerCase().contains(filter)));
//        });
//        tvMoviesTable.setItems(movies);
    }
    ObservableList<Movie> getMoviesList()
    {
        movies=FXCollections.observableArrayList();
        LinkedList<Movie>allMovies=DB.getAllMovies();
        movies.addAll(allMovies);
        return movies;
    }
    public void book() throws IOException {
        movies=tvMoviesTable.getItems();

        Movie selectedMovie=tvMoviesTable.getSelectionModel().getSelectedItem();
        globals.movieForTicket=DB.getMovieByID(selectedMovie.getID());
        globals.openNewForm("newTicket.fxml","New ticket");
    }
    public void viewTKTs() throws IOException {
        globals.openNewForm("viewUserTKT.fxml","Tickets");
    }
}