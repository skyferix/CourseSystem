package CourseWork.fxControllers;
import CourseWork.ds.*;
import CourseWork.hibernateControllers.conf;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Register implements Initializable {
    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public ChoiceBox userType;
    @FXML
    public TextField companyName;
    @FXML
    public TextField representative;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;

    //anchors
    @FXML
    public AnchorPane company;
    @FXML
    public AnchorPane person;

    @FXML
    public Button adminReg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> types = new ArrayList<String>();
        for (UserType ut: UserType.values() ) {
            if(ut!=UserType.Admin)
                types.add(ut.toString());
        }
        userType.getItems().addAll(types);
        userType.setValue(UserType.Person);

    }

    public void changeLayout(){

        person.setVisible(false);
        name.setText("");
        surname.setText("");
        company.setVisible(false);
        companyName.setText("");
        representative.setText("");
        adminReg.setVisible(false);

        if(userType.getValue().toString() == UserType.Person.toString()){
            person.setVisible(true);
        } else if (userType.getValue().toString() == UserType.Company.toString()) {
            company.setVisible(true);
        } else if (userType.getValue().toString() == UserType.Admin.toString()){
            adminReg.setVisible(true);
        }
    }

    public void submit(ActionEvent event) {
        String sUsername = username.getText().toString();
        String sPassword=password.getText().toString();
        String sName=name.getText().toString();
        String sSurname=surname.getText().toString();
        String sCompanyName=companyName.getText().toString();
        String sRepresentative=representative.getText().toString();

        if (sUsername != "") {
            if (conf.userHibControl.checkIfExists(sUsername)) {
                renderWrongForm(event);
                sendLoginAlert();
            }
        }
        if (sPassword == "" ||
                checkUserType(UserType.Person) && (sName == "" || sSurname == "") ||
                checkUserType(UserType.Company) && (sCompanyName == "" || sRepresentative == "")) {
            renderWrongForm(event);
            sendEmptyFieldsAlert();
        } else {
            User user = null;
            if(checkUserType(UserType.Person)) {
                user = new Person(sUsername,sPassword,sName,sSurname);
            } else if(checkUserType(UserType.Company)) {
                user = new Company(sUsername,sPassword,sCompanyName,sRepresentative);
            } else if(checkUserType(UserType.Admin))
                user = new Admin(sUsername, sPassword);

            conf.userHibControl.createUser(user);
            renderRightForm(event);
            String temp = conf.userHibControl.getUserIdByLogin(sUsername).toString();
            conf.session.put("userId", temp );
        }
    }

    private boolean checkUserType(UserType type) {
        return userType.getValue().toString() == type.toString();
    }

    private void renderWrongForm(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("register.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("fxControllers/Register/submit -> register.fxml");
        }
    }

    private void renderRightForm(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("fxControllers/Register/submit -> main.fxml");
        }
    }

    private void sendLoginAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("User with this username already exists");
        alert.showAndWait();
    }

    private void sendEmptyFieldsAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Fill all the fields");
        alert.showAndWait();
    }

}
