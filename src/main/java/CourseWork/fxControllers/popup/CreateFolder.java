package CourseWork.fxControllers.popup;

import CourseWork.Tree.FolderNode;
import CourseWork.Tree.TreeNode;
import CourseWork.ds.*;
import CourseWork.helpers.conf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;

public class CreateFolder {

    @FXML
    public TextField folderName;
    @FXML
    public TextArea folderDesc;

    public Folder parent;

    public TreeItem<TreeNode> parentNode;

    public void getAllDone(Folder folder, TreeItem<TreeNode> parentNode){
        this.parent = folder;
        this.parentNode = parentNode;
    }

    public void create(ActionEvent event){
        if(folderName.getText().equals("") || folderDesc.getText().equals("")){
            showAlert("Fields cannot be empty!");
            return;
        }

        Folder folder = new Folder(folderName.getText(),folderDesc.getText(), parent);
        conf.hibControl.create(folder);

        java.io.File newFolder = new File(parent.getPath() + "\\folder" + folder.getId());

        TreeItem<TreeNode> folderNode = new TreeItem<TreeNode>(new FolderNode(folder));
        parentNode.getChildren().add(folderNode);

        if(newFolder.mkdir()){
            this.close(event);
        } else {
            showAlert("Couldn't create folder!");
        }

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
