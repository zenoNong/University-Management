module com.example.placementmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    opens com.example.placementmanagementapp to javafx.fxml;
    exports com.example.placementmanagementapp;
}