module com.example.lab3bhanudahal {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.lab3bhanudahal to javafx.fxml;
    opens com.example.lab3bhanudahal.Model to javafx.base, javafx.fxml;

    exports com.example.lab3bhanudahal;
}