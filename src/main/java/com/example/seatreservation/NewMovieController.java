package com.example.seatreservation;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.seatreservation.globals.*;

public class NewMovieController implements Initializable {

    public Button saveButton;
    public TextArea taDescription;
    public DatePicker dpReleaseDate;
    public Spinner<Integer> tfScreenTime;
    public TextField tfMovieName;
    public TextField tfID;
    public AnchorPane mainPanel;
    public ImageView tfPath;
    public Button selectPathBtn;
    int id;
    Image image;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(previewedMovie==null) {
            id = globals.createNewRandomID(globals.moviesIDs);
            tfID.setText(Integer.toString(id));
            tfID.setEditable(false);
            spinnerTF(tfScreenTime);
            tfScreenTime.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200));
            dpReleaseDate.setValue(LocalDate.now());
        }
        else{
            id = previewedMovie.getID();
            tfID.setText(Integer.toString(id));
            tfID.setEditable(false);
            spinnerTF(tfScreenTime);
            tfScreenTime.setDisable(true);

            tfScreenTime.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(previewedMovie.getScreenTime(), previewedMovie.getScreenTime()+1));
            dpReleaseDate.setValue(previewedMovie.getReleaseDate());
            tfPath.setImage(previewedMovie.getImg());
            tfMovieName.setText(previewedMovie.getMovieName());
            taDescription.setText(previewedMovie.getDescription());
            tfMovieName.setDisable(true);
            taDescription.setDisable(true);
            selectPathBtn.setDisable(true);
            saveButton.setDisable(true);
            dpReleaseDate.setDisable(true);
        }
    }
    public void selectIMG(){
        try {
            FileChooser fileChooser=new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.JPG","*.jpeg","*.png","*.jpg"));
            String path= fileChooser.showOpenDialog(null).getAbsolutePath();
            Image image=new Image(path);
            this.image=image;
            tfPath.setImage(image);
        }catch (Exception e){
            showErrorAlert("Please select a picture");
        }

    }
    public void saveButtonA() {
        if (tfMovieName.getText().isEmpty()  || tfPath==null || taDescription.getText().isEmpty()||dpReleaseDate.getValue()==null) {
            showErrorAlert("Error: Please fill all information");
            return;
        }

        Movie movie=new Movie();
        movie.setID(id);
        movie.setMovieName(tfMovieName.getText());
        movie.setDescription(taDescription.getText());
        movie.setScreenTime(tfScreenTime.getValue());
        movie.setReleaseDate(dpReleaseDate.getValue());
        movie.setImg(image);
        DB.addMovie(movie);
        globals.showConfirmationAlert("Movie registered");
        Stage stage=(Stage) tfID.getScene().getWindow();
        stage.close();

    }
}
