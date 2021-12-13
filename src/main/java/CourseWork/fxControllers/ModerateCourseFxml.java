package CourseWork.fxControllers;

import CourseWork.Tree.FileNode;
import CourseWork.Tree.FolderNode;
import CourseWork.Tree.TreeNode;
import CourseWork.Tree.Type;
import CourseWork.ds.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;
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


    public void getAllDone(Course course) {
        Folder root = course.getMainFolder();
        nodeTitle.setText(root.getTitle());
        text1.setText(String.valueOf(Type.Folder));
        text2.setText(String.valueOf(root.getSubFolders().size()));
        text3.setText(String.valueOf(root.getFiles().size()));
        desc.setText("Description");
        textField.setText(root.getDescription());
        TreeItem<TreeNode> treeRoot = recursion(root);

        treeView.setRoot(treeRoot);
    }

    private TreeItem<TreeNode> recursion(Folder parent){
        TreeNode parentNode = new FolderNode(parent);
        TreeItem<TreeNode> parentTreeItem = new TreeItem<TreeNode>(parentNode);

        List<File> files = parent.getFiles();
        List<TreeNode> fileNodes = files.stream().map(FileNode::new).collect(Collectors.toList());

        for(TreeNode node: fileNodes){
            parentTreeItem.getChildren().add(new TreeItem<>(node));
        }
        for(Folder folder: parent.getSubFolders()){
            parentTreeItem.getChildren().add(recursion(folder));
        }
        return parentTreeItem;
    }

    public void createFile(){
        TreeItem<TreeNode> node = treeView.getSelectionModel().getSelectedItem();
    }

    public void createFolder(){
        TreeItem<TreeNode> node = treeView.getSelectionModel().getSelectedItem();
    }

    public void selectItem(){
        TreeItem<TreeNode> node = treeView.getSelectionModel().getSelectedItem();
        if(node.getValue().getType() == Type.Folder){

        } else {

        }
    }
}
