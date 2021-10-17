package CourseWork.ds;

import javax.persistence.Entity;

@Entity
public class Company extends User {
    private String companyName;
    private String representative;

    public Company(String login, String password, String companyName, String representative) {
        super(login, password, UserType.Company);
        this.companyName = companyName;
        this.representative = representative;
    }

    public Company() {

    }

    public String getName() {
        return companyName;
    }

    public void setName(String companyName) {
        this.companyName = companyName;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }
}
