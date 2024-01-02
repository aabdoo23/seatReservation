module com.example.seatreservation {
    requires javafx.controls;
    requires javafx.fxml;
//    requires charm.glisten;
    requires java.sql;
    requires java.desktop;


    opens com.example.seatreservation to javafx.fxml;
    exports com.example.seatreservation;
}