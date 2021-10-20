package CourseWork.ds;

import javax.persistence.Entity;

@Entity
public class Admin extends User{
    public Admin(String login, String password) {
        super(login, password, UserType.Admin);
    }

    private String name;
    private String email;
    public Admin() {

    }

    public Admin(String login, String password, String name, String email) {
        super(login, password, UserType.Admin);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
