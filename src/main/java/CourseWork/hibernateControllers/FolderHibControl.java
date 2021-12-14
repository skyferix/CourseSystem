package CourseWork.hibernateControllers;

import CourseWork.ds.Course;
import CourseWork.ds.Folder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class FolderHibControl {
    private EntityManagerFactory emf = null;

    public FolderHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Folder getFolderById(int id) {
        EntityManager em = null;
        Folder folder = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            folder = em.find(Folder.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such course by given Id");
        }
        return folder;
    }

}
