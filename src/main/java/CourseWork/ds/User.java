package CourseWork.ds;

import CourseWork.Helper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    @ManyToMany
    private List<Course> moderatedCourses;

    @ManyToMany
    private List<Folder> folders;

    public User(String login, String password, UserType type) {
        this.login = login;
        this.password = Helper.hashPwd(password);
        this.userType = type;
        this.moderatedCourses = new ArrayList<>();
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Course> getModeratedCourses() {
        return moderatedCourses;
    }

    public void setModeratedCourses(List<Course> moderatedCourses) {
        this.moderatedCourses =moderatedCourses;
    }

    public List<Folder> getMyFolders() {
        return folders;
    }

    public void setMyFolders(List<Folder> folders) {
        this.folders = folders;
    }
}
