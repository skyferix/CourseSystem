package CourseWork.fxControllers.popup;

import CourseWork.Tree.FileNode;
import CourseWork.Tree.TreeNode;
import CourseWork.ds.*;
import CourseWork.helpers.conf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {

    @FXML
    public TextField fileName;
    @FXML
    public TextArea fileContent;

    public Folder parent;

    public TreeItem<TreeNode> parentNode;

    public void getAllDone(Folder folder, TreeItem<TreeNode> parentNode){
        this.parent = folder;
        this.parentNode = parentNode;
    }

    public void create(ActionEvent event){
        if(fileName.getText().equals("") || fileContent.getText().equals("")){
            showAlert("Fields cannot be empty!");
            return;
        }

        CourseWork.ds.File file = new CourseWork.ds.File(fileName.getText(), parent);
        conf.hibControl.create(file);

        java.io.File newFile = new File(parent.getPath() + "\\file" + file.getId() + ".txt");


        TreeItem<TreeNode> folderNode = new TreeItem<TreeNode>(new FileNode(file));
        parentNode.getChildren().add(folderNode);

        try {
            if(newFile.createNewFile()){
                this.close(event);
            } else {
                showAlert("Couldn't create folder!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.setPath(newFile.getPath());
        conf.hibControl.edit(file);

        try {
            FileWriter fileWriter = new FileWriter(newFile.getPath());
            fileWriter.write(fileContent.getText());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.close(event);
    }

    public void close(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
