package CourseWork.fxControllers;

import CourseWork.hibernateControllers.CourseHibControl;
import CourseWork.hibernateControllers.UserHibControl;
import CourseWork.ds.Course;
import CourseWork.ds.Folder;
import CourseWork.hibernateControllers.conf;
import CourseWork.view.CourseView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;

public class Main implements Initializable {
    public ListView courseList;
    public TreeView courseFolderTree;

    @FXML
    public TableView<CourseView> tableView;
    @FXML
    public TableColumn<CourseView, String> title;
    @FXML
    public TableColumn<CourseView, LocalDate> startDate;
    @FXML
    public TableColumn<CourseView, LocalDate> endDate;


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
        title.setCellValueFactory(new PropertyValueFactory<CourseView, String>("title"));
        startDate.setCellValueFactory(new PropertyValueFactory<CourseView, LocalDate>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<CourseView, LocalDate>("endDate"));

        tableView.setItems(getCourses(courses));
    }

    private ObservableList<CourseView> getCourses(List<Course> courses){
        ObservableList<CourseView> fcourses = FXCollections.observableArrayList();
//        for (Course course: courses) {
//            fcourses.add(new CourseView(course.getTitle(), course.getStartDate(), course.getEndDate()));
//        }
        fcourses.add(new CourseView("hello", LocalDate.of(2019, Month.APRIL,12), LocalDate.of(2019, Month.APRIL,12)));
        fcourses.add(new CourseView("hello", LocalDate.of(2019, Month.APRIL,12), LocalDate.of(2019, Month.APRIL,12)));

        return fcourses;
    }
}
