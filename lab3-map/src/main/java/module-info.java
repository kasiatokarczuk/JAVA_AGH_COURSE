module org.example.lab3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens org.example.lab3 to javafx.fxml;
    exports org.example.lab3;
}