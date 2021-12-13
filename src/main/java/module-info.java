module com.example.coursesys {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.prefs;


    opens CourseWork to javafx.fxml;
    exports CourseWork;

    opens CourseWork.ds to javafx.fxml, org.hibernate.orm.core, java.persistence;
    exports CourseWork.ds;
    exports CourseWork.fxControllers to javafx.fxml;
    opens CourseWork.fxControllers to javafx.fxml;
    exports CourseWork.helpers;
    opens CourseWork.helpers to javafx.fxml;
    exports CourseWork.Tree;
    opens CourseWork.Tree to javafx.fxml;
}