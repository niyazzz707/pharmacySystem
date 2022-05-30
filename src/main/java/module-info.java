/**
 *
 */
module com.sample.pharmacy {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires com.jfoenix;


    opens com.sample.pharmacy to javafx.fxml;
    exports com.sample.pharmacy;
}