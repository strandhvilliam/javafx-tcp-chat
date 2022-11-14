module com.example.javafxtcpchat {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.javafxtcpchat.server;
    opens com.example.javafxtcpchat.server to javafx.fxml;
    exports com.example.javafxtcpchat.client;
    opens com.example.javafxtcpchat.client to javafx.fxml;
}