package CourseWork.fxControllers;

import CourseWork.Tree.FileNode;
import CourseWork.Tree.FolderNode;
import CourseWork.Tree.TreeNode;
import CourseWork.Tree.Type;
import CourseWork.ds.*;
import CourseWork.fxControllers.popup.CreateFile;
import CourseWork.fxControllers.popup.CreateFolder;
import CourseWork.helpers.conf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ModerateCourseFxml{

    @FXML
    public TreeView<TreeNode> treeView;
    @FXML
    public Button createFile;
    @FXML
    public Button createFolder;
    @FXML
    public Button goBack;
    @FXML
    public Text nodeTitle;
    @FXML
    public Text text1;
    @FXML
    public Text text2;
    @FXML
    public Text text3;
    @FXML
    public Text desc;
    @FXML
    public TextField textField;
    @FXML
    public AnchorPane mainAnchor;

    public Course mainCourse;


    public void getAllDone(Course course) {
        mainCourse = course;
        Folder root = course.getMainFolder();
        TreeItem<TreeNode> treeRoot = recursion(root);
        treeView.setRoot(treeRoot);
    }

    private TreeItem<TreeNode> recursion(Folder parent){
        TreeNode parentNode = new FolderNode(parent);
        String folderPath = conf.rootFolder.getPath() + "\\photo" + "\\folder.png";
        String filePath = conf.rootFolder.getPath() + "\\photo" + "\\file.png";
        TreeItem<TreeNode> parentTreeItem = null;
        ImageView folderImg = null;
        try {
            folderImg = new ImageView(new Image(new FileInputStream(folderPath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        folderImg.setFitHeight(16);
        folderImg.setFitWidth(16);

            parentTreeItem = new TreeItem<TreeNode>(parentNode,folderImg);


        List<File> files = parent.getFiles();
        List<TreeNode> fileNodes = files.stream().map(FileNode::new).collect(Collectors.toList());
        ImageView fileImg = null;
        try {
            fileImg = new ImageView(new Image(new FileInputStream(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileImg.setFitHeight(16);
        fileImg.setFitWidth(16);
        for(TreeNode node: fileNodes){
            parentTreeItem.getChildren().add(new TreeItem<>(node,fileImg));
        }
        for(Folder folder: parent.getSubFolders()){
            parentTreeItem.getChildren().add(recursion(folder));
        }
        return parentTreeItem;
    }

    public void createFile(){
        System.out.println("file");
        TreeItem<TreeNode> node = treeView.getSelectionModel().getSelectedItem();
        if(node.getValue().getType() == Type.Folder) {
            Folder folder = conf.folderHibControl.getFolderById(node.getValue().getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popup/create-file.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CreateFile createFile = loader.getController();
            createFile.getAllDone(folder, node);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void createFolder(){
        System.out.println("folder");
        TreeItem<TreeNode> node = treeView.getSelectionModel().getSelectedItem();

        if(node.getValue().getType() == Type.Folder) {
            Folder folder = conf.folderHibControl.getFolderById(node.getValue().getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popup/create-folder.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CreateFolder createFolder = loader.getController();
            createFolder.getAllDone(folder, node);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void selectItem(){
        TreeItem<TreeNode> node = treeView.getSelectionModel().getSelectedItem();
        if(node == null){
            return;
        }
        nodeTitle.setText(node.getValue().getName());
        text1.setText("Entity: " + String.valueOf(node.getValue().getType()));

        User user = conf.userHibControl.getUserById(conf.getUserId());
        Boolean bool = false;
        for(Course course: user.getModeratedCourses()){
            if(course.getId() == mainCourse.getId()){
                bool=true;
                break;
            }
        }
        if(user.getUserType() == UserType.Person){
            if(mainCourse.getOwner().getId() == user.getId()){
                bool=true;
            }
        }
        if(node.getValue().getType() == Type.Folder){
            if(bool) {
                createFolder.setVisible(true);
                createFile.setVisible(true);
            }
            Folder folder = conf.folderHibControl.getFolderById(node.getValue().getId());
            text2.setText("Sub folders number: " + String.valueOf(folder.getSubFolders().size()));
            text3.setText("Files number: " + String.valueOf(folder.getFiles().size()));
            desc.setText("Description");
            textField.setText(folder.getDescription());
        } else {
            createFolder.setVisible(false);
            createFile.setVisible(false);

            File file = conf.fileHibControl.getFileById(node.getValue().getId());
            java.io.File readFile = new java.io.File(file.getPath());
            StringBuilder rez = new StringBuilder();
            try {
                Scanner scanner = new Scanner(readFile);
                while (scanner.hasNextLine()){
                    rez.append(scanner.nextLine());
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            textField.setText(rez.toString());
            desc.setText("Content");
            text2.setText("File size in bytes: " + String.valueOf(readFile.length()));
            text3.setText("");
        }
        mainAnchor.setVisible(true);
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

    public void textField(){}
}
