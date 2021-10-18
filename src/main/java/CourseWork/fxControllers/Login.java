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
                System.out.println(getClass().getResource("main.fxml"));
                root = FXMLLoader.load(getClass().getResource("main.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                String temp = conf.userHibControl.getUserIdByLogin(sUsername).toString();
                conf.session.put("userId", temp );
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
}
