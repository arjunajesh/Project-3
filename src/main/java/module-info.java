module com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.testng;
    requires org.junit.jupiter.api;


    opens com.example.project3 to javafx.fxml;
    exports com.example.project3;
}