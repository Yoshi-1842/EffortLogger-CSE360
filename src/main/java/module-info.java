module com.example.effortlogger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.effortlogger to javafx.fxml;
    exports com.example.effortlogger;
}