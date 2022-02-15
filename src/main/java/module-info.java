module com.katapios {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.katapios to javafx.fxml;
    exports com.katapios;
}