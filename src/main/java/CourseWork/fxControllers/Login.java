package CourseWork.fxControllers;

import CourseWork.ds.User;
import CourseWork.helpers.conf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    private Parent root;
    private Scene scene;
    private Stage stage;


    @FXML
    public TextField username;
    @FXML
    public TextField password;

    public void submit(ActionEvent event) {
        String sUsername = username.getText().toString();
        String sPassword = password.getText().toString();
        User user = conf.userHibControl.getUserByLogin(sUsername, sPassword);
        if(user != null){
            try {
                int temp = conf.userHibControl.getUserIdByLogin(sUsername);
                conf.putUserId(temp);
                root = FXMLLoader.load(getClass().getResource("courses.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch(IOException e){
                e.printStackTrace();
                System.out.println("fxControllers/Login/submit -> main");
            }
        } else {
            try {
                root = FXMLLoader.load(getClass().getResource("wrong-login.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch(IOException e){
                e.printStackTrace();
                System.out.println("fxControllers/Login/submit -> wrong-login");
            }
        }
    }
    public void goBack(ActionEvent event){
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
}
