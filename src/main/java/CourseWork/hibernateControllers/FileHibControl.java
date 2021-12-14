package CourseWork.hibernateControllers;

import CourseWork.ds.File;
import CourseWork.ds.Folder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class FileHibControl {
    private EntityManagerFactory emf = null;

    public FileHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public File getFileById(int id) {
        EntityManager em = null;
        File file = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            file = em.find(File.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such course by given Id");
        }
        return file;
    }

}
