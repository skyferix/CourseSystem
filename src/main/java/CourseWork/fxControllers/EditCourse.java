package CourseWork.fxControllers;

import CourseWork.ds.Course;
import CourseWork.ds.Person;
import CourseWork.ds.User;
import CourseWork.ds.UserType;
import CourseWork.helpers.conf;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditCourse {

    @FXML
    public TextField title;
    @FXML
    public DatePicker startDate;
    @FXML
    public DatePicker endDate;
    @FXML
    public TextField description;
    @FXML
    public ListView<String> moderators;
    @FXML
    public AnchorPane myCourseDetails;


    public void getAllDone(Course course) {
        moderators.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            List<User> users = course.getCourseModerators();
            for (User user: users){
                Person person = (Person)user;
                moderators.getItems().add(person.getName());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < moderators.getItems().size(); i++) {
            moderators.getSelectionModel().select(i);
        }
        List<User> users = conf.userHibControl.getUsersByType(UserType.Person);
        int userId = conf.getUserId();
        users.stream().filter(user->user.getId()==userId).findFirst().ifPresent(users::remove);
        List<User> mod = course.getCourseModerators();
        for (User u: users ) {
            boolean h = true;
            for(User m:mod){
                if(m.getLogin().equals(u.getLogin())){
                    h=false;
                    break;
                }
            }
            if(h){
                Person person = (Person)u;
                moderators.getItems().add(person.getName());
            }
        }
        title.setText(course.getTitle());
        startDate.setValue(course.getStartDate());
        endDate.setValue(course.getEndDate());
        description.setText(course.getDescription());
    }

    public void goBack(ActionEvent event){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("courses.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void submit(ActionEvent event){
        String sTitle = title.getText();
        LocalDate sStartDate = startDate.getValue();
        LocalDate sEndDate = endDate.getValue();
        String sDescription = description.getText();
        if(sTitle != "" && sDescription != "" && sStartDate != null && sEndDate != null)
        {
            int userId = conf.getUserId();
            Person person = conf.personHibControl.getPersonById(userId);
            Course course = conf.courseHibControl.getCourseByTitle(sTitle);
            List<String> test = moderators.getSelectionModel().getSelectedItems();
            List<User> users = new ArrayList<>();
            List<Person> temp = null;
            for (String s: test) {
                try {
                    temp = conf.personHibControl.getPersonByName(s);
                    for (Person p : temp) {
                        users.add((User) p);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            course.setCourseModerators(users);
            conf.hibControl.edit(course);
            goBack(event);
        } else {
            showAlert();
        }
    }
    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Not all field ar filled in!");
        alert.showAndWait();
    }
}
