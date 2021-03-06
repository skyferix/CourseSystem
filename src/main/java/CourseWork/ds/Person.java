package CourseWork.ds;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person extends User {
    private String name;
    private String surname;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name="course_participants",
            joinColumns = @JoinColumn(name="Course_id"),
            inverseJoinColumns = @JoinColumn(name="Participant_id")
    )
    private List<Course> enrolledCourses;

    @OneToMany(mappedBy ="owner",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Course> ownedCourses;

    public Person() {

    }

    @Override
    public String toString(){
        return getUserType() + " " +
                name + ' ' +
                surname + ' ' +
                getLogin() + " " +
                getPassword();
    }

    public Person(String login, String password, String name, String surname, UserType type) {
        super(login, password, type);
        this.name = name;
        this.surname = surname;
    }

    public Person(String login, String password, String name, String surname) {
        super(login, password, UserType.Person);
        this.name = name;
        this.surname = surname;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Course> getOwnedCourses() {
        return ownedCourses;
    }

    public void setOwnedCourses(List<Course> ownedCourses) {
        this.ownedCourses = ownedCourses;
    }

    public void addEnrolledCourse(Course course){
        this.getEnrolledCourses().add(course);
    }
    public void removeEnrolledCourse(Course course){
        this.getEnrolledCourses().removeIf(item -> item.getTitle().equals(course.getTitle()));
    }
}
