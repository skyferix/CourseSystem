package CourseWork.Tree;

import CourseWork.ds.Folder;

public class FolderNode extends TreeNode {

    public FolderNode(Folder folder) {
        super(folder.getTitle(), folder.getId(),Type.Folder);
    }
}
