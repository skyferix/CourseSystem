package CourseWork.fxControllers;

import CourseWork.ds.Course;
import CourseWork.helpers.conf;
import CourseWork.view.CourseView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;


public class Main implements Initializable {
//    public ListView courseList;
//    public TreeView courseFolderTree;

    @FXML
    public TableView<CourseView> table;
    @FXML
    public TableColumn<CourseView, SimpleStringProperty> title;
    @FXML
    public TableColumn<CourseView, SimpleStringProperty> startDate;
    @FXML
    public TableColumn<CourseView, SimpleStringProperty> endDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(conf.session.get("userId","none"));
        List<Course> courses = conf.courseHibControl.getAllCourses();
//        tableView.setEditable(true);
//        TableView table = new TableView<CourseView>();
//        TableColumn<CourseView, String> col1 = new TableColumn<CourseView, String>("Title");
//        col1.setCellValueFactory(new PropertyValueFactory<CourseView, String>("title"));
//
//        TableColumn<CourseView, LocalDate> col2 = new TableColumn<CourseView, LocalDate>("Start Date");
//        col2.setCellValueFactory(new PropertyValueFactory<CourseView, LocalDate>("startDate"));
//
//        TableColumn<CourseView, LocalDate> col3 = new TableColumn<CourseView, LocalDate>("EndDate");
//        col3.setCellValueFactory(new PropertyValueFactory<CourseView,LocalDate>("endDate"));
//
//        table.getColumns().addAll(col1,col2,col3);
//        table.setItems(getCourses(courses));
//        tableView = table;
//        tableView.refresh();
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        table.setItems(getCourses(courses));


        TableColumn<CourseView, String> col1 = new TableColumn<CourseView, String>("Title");
        col1.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<CourseView, LocalDate> col2 = new TableColumn<CourseView, LocalDate>("Start Date");
        col2.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<CourseView, LocalDate> col3 = new TableColumn<CourseView, LocalDate>("End Date");
        col3.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableView<CourseView> table2 = new TableView<CourseView>();
        table2.getColumns().addAll(col1,col2,col3);
        table2.setItems(getCourses(courses));
        table2.getItems().clear();
        table2.getItems().addAll(getCourses(courses));

//        table.setItems(getCourses(courses));
        VBox vbox = new VBox();
        vbox.getChildren().addAll(table2);

        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<CourseView> getCourses(List<Course> courses){
        ObservableList<CourseView> fcourses = FXCollections.observableArrayList();
//        for (Course course: courses) {
//            fcourses.add(new CourseView(course.getTitle(), course.getStartDate(), course.getEndDate()));
//        }
        fcourses.add(new CourseView("hello", "hello","hello"));
//        fcourses.add(new CourseView("hello", LocalDate.of(2019, Month.APRIL,12).toString(), LocalDate.of(2019, Month.APRIL,12).toString()));
//        fcourses.add(new CourseView("hello", LocalDate.of(2019, Month.APRIL,12).toString(), LocalDate.of(2019, Month.APRIL,12).toString()));

        return fcourses;
    }
}
