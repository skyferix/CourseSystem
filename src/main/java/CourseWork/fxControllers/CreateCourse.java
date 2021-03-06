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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateCourse implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moderators.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            List<User> users = conf.userHibControl.getUsersByType(UserType.Person);
            int userId = conf.getUserId();
            users.stream().filter(user->user.getId()==userId).findFirst().ifPresent(users::remove);
            for (User user: users){
                Person person = (Person)user;
                moderators.getItems().add(person.getName());
            }
        } catch (Exception e){
        }
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
            Course course = new Course(sTitle,sDescription,LocalDate.now(),sStartDate,sEndDate,person);
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
            conf.hibControl.create(course);
            users.stream().filter(user->user.getId()==userId).findFirst().ifPresent(users::remove);
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
