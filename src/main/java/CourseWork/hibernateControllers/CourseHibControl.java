package CourseWork.hibernateControllers;

import CourseWork.ds.Course;
import CourseWork.ds.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class CourseHibControl {
    private EntityManagerFactory emf = null;

    public CourseHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<Course> getAllCourses() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Course.class));
            Query q = em.createQuery(query);

            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public Course getCourseById(int id) {
        EntityManager em = null;
        Course course = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            course = em.find(Course.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such course by given Id");
        }
        return course;
    }

    public Course getCourseByTitle(String title){
        EntityManager em = null;
        Course course = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Object temp = em.createNativeQuery("SELECT id FROM course u WHERE u.title = :title")
                    .setParameter("title", title)
                    .getSingleResult();
            course = getCourseById((Integer) temp);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such course by given name");
        }
        return course;
    }

    public boolean checkIfParticipant(int courseId, int userId){
        EntityManager em = null;
        Course course = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
        Object temp = em.createNativeQuery("SELECT * FROM course_participants cp " +
                        "WHERE cp.course_id = :courseId and cp.participant_id = :userId")
                .setParameter("courseId", courseId)
                .setParameter("userId", userId)
                .getSingleResult();
        } catch (NoResultException e){
            return false;
        } catch (Exception e){
            System.out.println("No such course/participant record");
        }
        return true;
    }

    public List<Course> getCoursesByUserId(int id){
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cr = cb.createQuery(Course.class);
        Root<Course> root = cr.from(Course.class);
        Path<Integer> test = root.join("owner").get("id");
        cr.select(root).where(cb.equal(test, id));
        Query query = em.createQuery(cr);
        return query.getResultList();
    }
}
