package CourseWork.Tree;

public abstract class TreeNode {
    private String displayName = "";
    private int id;
    private Type type;

    public TreeNode(String displayName, int id, Type type){
        setName(displayName);
        setId(id);
        setType(type);
    }

    public void setName(String name) {
        this.displayName = name;
    }

    public String getName() {
        return displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return displayName;
    }
}
