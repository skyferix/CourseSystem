package CourseWork.ds;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Hib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Hib(int id) {
        this.id = id;
    }

    public Hib() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
