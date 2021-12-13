package CourseWork.ds;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Folder extends Hib {
    private String title;

    @OneToOne(mappedBy = "mainFolder")
    private Course parentCourse;

    @OneToMany(mappedBy = "parentFolder",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("id ASC")
    private List<Folder> subFolders;

    @ManyToOne
    @JoinColumn(name="parent_folder_id", referencedColumnName = "id")
    private Folder parentFolder;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="folder_id")
    @OrderBy("id ASC")
    private List<File> files;

    @ManyToMany(mappedBy = "folders", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("id ASC")
    private List<User> moderators = new ArrayList<>();

    private String description;

    public Folder() {
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    public Folder(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course getParentCourse() {
        return parentCourse;
    }

    public void setParentCourse(Course parentCourse) {
        this.parentCourse = parentCourse;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<Folder> subFolders) {
        this.subFolders = subFolders;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<User> getEditors() {
        return moderators;
    }

    public void setEditors(List<User> moderators) {
        this.moderators = moderators;
    }

    @Override
    public String toString(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
