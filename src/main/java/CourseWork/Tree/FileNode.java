package CourseWork.Tree;

import CourseWork.ds.File;

public class FileNode extends TreeNode{

    public FileNode(File file) {
        super(file.getName(), file.getId(), Type.File);
    }
}
