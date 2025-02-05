module org.example.zad4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.zad4 to javafx.fxml;
    exports org.example.zad4;
}