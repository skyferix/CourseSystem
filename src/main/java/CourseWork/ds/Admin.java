package CourseWork.ds;

import javax.persistence.Entity;

@Entity
public class Admin extends User{
    public Admin(String login, String password) {
        super(login, password, UserType.Admin);
    }

    public Admin() {

    }
}
