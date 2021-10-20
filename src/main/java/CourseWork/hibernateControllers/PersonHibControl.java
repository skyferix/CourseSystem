package CourseWork.hibernateControllers;

import CourseWork.ds.Course;
import CourseWork.ds.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class PersonHibControl {
    private EntityManagerFactory emf = null;

    public PersonHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Person getPerson(Person person) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            person = em.find(Person.class, person.getId());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such course by given Id");
        }
        return person;
    }
    public Person getPersonById(int id) {
        EntityManager em = null;
        Person person = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            person = em.find(Person.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such course by given Id");
        }
        return person;
    }
    public List<Person> getPersonByName(String name) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);
        cr.select(root).where(cb.equal(root.get("name"), name));
        Query query = em.createQuery(cr);
        return query.getResultList();
    }
}
