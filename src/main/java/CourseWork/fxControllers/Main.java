package CourseWork.fxControllers;

import CourseWork.ds.Course;
import CourseWork.ds.Person;
import CourseWork.ds.User;
import CourseWork.ds.UserType;
import CourseWork.helpers.conf;
import CourseWork.view.CourseView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Main implements Initializable {
//    public ListView courseList;
//    public TreeView courseFolderTree;
    @FXML
    public TabPane tabPane;

    @FXML
    public Tab moderateCoursesTab;

    @FXML
    public Text title;
    @FXML
    public TextField creator;
    @FXML
    public TextField startDate;
    @FXML
    public TextField endDate;
    @FXML
    public ListView<String> list;
    @FXML
    public TextField description;
    @FXML
    public AnchorPane courseDetails;
    @FXML
    public Button participate;
    @FXML
    public Button revokeParticipate;

    //second
    @FXML
    public ListView<String> myList;
    @FXML
    public Text myTitle;
    @FXML
    public TextField myStartDate;
    @FXML
    public TextField myEndDate;
    @FXML
    public TextField myDescription;
    @FXML
    public AnchorPane myCourseDetails;
    @FXML
    public Tab myCourseTab;
    @FXML
    public Tab adminTab;

    //third
    @FXML
    public ListView<String> myList1;
    @FXML
    public Text myTitle1;
    @FXML
    public TextField creator1;
    @FXML
    public TextField myStartDate1;
    @FXML
    public TextField myEndDate1;
    @FXML
    public TextField myDescription1;
    @FXML
    public AnchorPane myCourseDetails1;
    @FXML
    public Tab myCourseTab1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = conf.userHibControl.getUserById(conf.getUserId());
        if(user.getUserType()==UserType.Admin){
            adminTab.setDisable(false);
        } else{
            adminTab.setDisable(true);
        }
        if(user.getUserType()==UserType.Person){
            myCourseTab.setDisable(false);
        } else{
            myCourseTab.setDisable(true);
        }
        if(user.getModeratedCourses().size()>0){
            moderateCoursesTab.setDisable(false);
        } else{
            moderateCoursesTab.setDisable(true);
        }
        List<Course> courses = conf.courseHibControl.getAllCourses();
        list.getItems().addAll(getCourseNames(courses));
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentItem = list.getSelectionModel().getSelectedItem();
                Course course = conf.courseHibControl.getCourseByTitle(currentItem);
                title.setText(course.getTitle());
                creator.setText(course.getOwner().getName());
                startDate.setText(course.getStartDate().toString());
                endDate.setText(course.getEndDate().toString());
                description.setText(course.getDescription());
                courseDetails.setVisible(true);

                int userId = Integer.parseInt(conf.session.get("userId","none"));
                User user = conf.userHibControl.getUserById(userId);
                participate.setVisible(false);
                revokeParticipate.setVisible(false);

                if(user.getUserType() == UserType.Person) {
                    Person person = (Person) user;
                    List<Course> courses = person.getOwnedCourses();

                    for(Course c: courses){
                        if(c.getId() == course.getId()){
                            return;
                        }
                    }
                    if (conf.courseHibControl.checkIfParticipant(course.getId(), userId)) {
                        revokeParticipate.setVisible(true);
                    } else {
                        participate.setVisible(true);
                    }
                }
            }
        });
        int userId = conf.getUserId();
        List<Course> myCourses = conf.courseHibControl.getCoursesByUserId(userId);
        myList.getItems().addAll(getCourseNames(myCourses));
        myList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentItem = myList.getSelectionModel().getSelectedItem();
                Course course = conf.courseHibControl.getCourseByTitle(currentItem);
                myTitle.setText(course.getTitle());
                myStartDate.setText(course.getStartDate().toString());
                myEndDate.setText(course.getEndDate().toString());
                myDescription.setText(course.getDescription());
                myCourseDetails.setVisible(true);
            }
        });

        List<Course> moderatedCourses = conf.userHibControl.getUserById(conf.getUserId()).getModeratedCourses();
        System.out.println(moderatedCourses);
        myList1.getItems().addAll(getCourseNames(moderatedCourses));
        myList1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentItem = myList1.getSelectionModel().getSelectedItem();
                Course course = conf.courseHibControl.getCourseByTitle(currentItem);
                creator1.setText(course.getOwner().getName());
                myTitle1.setText(course.getTitle());
                myStartDate1.setText(course.getStartDate().toString());
                myEndDate1.setText(course.getEndDate().toString());
                myDescription1.setText(course.getDescription());
                myCourseDetails1.setVisible(true);
            }
        });

    }

    public void participate(){
        String courseTitle = list.getSelectionModel().getSelectedItem();
        Course course = conf.courseHibControl.getCourseByTitle(courseTitle);
        int userId = Integer.parseInt(conf.session.get("userId","none"));
        Person person = conf.personHibControl.getPersonById(userId);
        person.addEnrolledCourse(course);
        conf.hibControl.edit(person);
        participate.setVisible(false);
        revokeParticipate.setVisible(true);
    }

    public void revokeParticipate() {
        String courseTitle = list.getSelectionModel().getSelectedItem();
        Course course = conf.courseHibControl.getCourseByTitle(courseTitle);
        int userId = Integer.parseInt(conf.session.get("userId","none"));
        Person person = conf.personHibControl.getPersonById(userId);
        person.removeEnrolledCourse(course);
        conf.hibControl.edit(person);
        participate.setVisible(true);
        revokeParticipate.setVisible(false);
    }

    private List<String> getCourseNames(List<Course> courses){
        ArrayList<String> sCourses = new ArrayList<String>();
        for (Course course: courses) {
            sCourses.add(course.getTitle());
        }
        return sCourses;
    }

    public void createCourse(ActionEvent event) {
        callCourseWindow(event);
    }

    private void callCourseWindow(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("create-course.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logOut(ActionEvent event){
        conf.resetUserId();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/CourseWork/start.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goEdit(ActionEvent event){
        Parent root = null;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String currentItem = myList.getSelectionModel().getSelectedItem();
        Course course = conf.courseHibControl.getCourseByTitle(currentItem);

        try {
            System.out.println(getClass().getResource("edit-course.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-course.fxml"));
            root = loader.load();
            EditCourse editCourse = loader.getController();
            editCourse.getAllDone(course);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goModerate(ActionEvent event){
        String currentItem = list.getSelectionModel().getSelectedItem();
        this.goToModerateWindow(currentItem, event);
    }

    public void goModerate1(ActionEvent event){
        String currentItem = myList.getSelectionModel().getSelectedItem();
        this.goToModerateWindow(currentItem, event);
    }

    public void goModerate2(ActionEvent event){
        String currentItem = myList1.getSelectionModel().getSelectedItem();
        this.goToModerateWindow(currentItem, event);
    }

    private void goToModerateWindow(String currentItem, ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Course course = conf.courseHibControl.getCourseByTitle(currentItem);
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("moderate-course.fxml"));
            root = loader.load();
            ModerateCourseFxml editCourse = loader.getController();
            editCourse.getAllDone(course);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
