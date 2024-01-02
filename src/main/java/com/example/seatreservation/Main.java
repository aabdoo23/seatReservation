package com.example.seatreservation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Objects;

public class Main extends Application {
    static AnchorPane root=new AnchorPane();
//    static List<AnchorPane>grids=new ArrayList<>();
//    private static int cur_idx=0;
//    Scene scene=new Scene(root);
    @Override
    public void start(Stage stage) throws IOException {
        root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainMenu.fxml")));
        Scene scene = new Scene(root, 820, 555);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }



//    public static AnchorPane getPane(int idx) {
//        return grids.get(idx);
//    }
//    public static void setPane(int idx){
//        root=getPane(idx);
//        root.getChildren().remove(grids.get(cur_idx));
//        root.getChildren().add(grids.get(idx));
//        cur_idx=idx;
//    }

    public static void main(String[] args) throws SQLException {

        new DB();
        launch();
    }

}