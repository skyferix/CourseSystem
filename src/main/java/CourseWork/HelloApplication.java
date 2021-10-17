package CourseWork;

import CourseWork.ds.*;
import CourseWork.hibernateControllers.conf;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.prefs.Preferences;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        createUser();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    public void createUser(){
//        conf.courseHibControl.createCourse(course);
//        User user = conf.userHibControl.getUserById(2);
//        List<Course> courses = new ArrayList<Course>();
//        courses.add(course);
//        user.setModeratedCourses(courses);
//        conf.userHibControl.addModerators(user);
//        Course course = new Course("name","desc", conf.parseDate("2015-05-12"), conf.parseDate("2016-08-12"), new Date() );
//        Admin admin = new Admin("root", "root");

//        UserHibControl userHibControl = new UserHibControl(entityManagerFactory);
//        conf.userHibControl.createUser(admin);
//        conf.courseHibControl.createCourse(course);
//        CourseHibControl courseHibControl = new CourseHibControl(entityManagerFactory);
//        UserHibControl userHibControl = new UserHibControl(entityManagerFactory);
//        userHibControl.createUser(person);

    }


    public static void main(String[] args) {
        launch();
    }
}