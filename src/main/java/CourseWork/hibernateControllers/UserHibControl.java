package CourseWork.hibernateControllers;

import CourseWork.ds.UserType;
import CourseWork.helpers.Helper;
import CourseWork.ds.User;
import CourseWork.helpers.dbUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.util.List;

public class UserHibControl {
    private EntityManagerFactory emf = null;

    public UserHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<User> getAllUsers() {
        return getAllUsers(false, -1, -1);
    }

    public List<User> getAllUsers(boolean all, int resMax, int resFirst) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(User.class));
            Query q = em.createQuery(query);

            if (!all) {
                q.setMaxResults(resMax);
                q.setFirstResult(resFirst);
            }

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

    public User getUserById(int id) {
        EntityManager em = null;
        User user = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.find(User.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return user;
    }

    public User getUserByLogin(String login, String password){
        EntityManager em = null;
        User user = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            Object temp = em.createNativeQuery("SELECT id FROM user u WHERE u.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
            user = getUserById((Integer) temp);
            if (user.getPassword().equals(Helper.hashPwd(password))) {
                return user;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public Integer getUserIdByLogin(String login){
        EntityManager em = null;
        Object temp = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            temp = em.createNativeQuery("SELECT id FROM user u WHERE u.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("userhib/getUserIdByLogin failed");
        }
        return (Integer) temp;
    }

    public boolean checkIfExists(String login){
        EntityManager em = null;
        try{
            em=getEntityManager();
            em.getTransaction().begin();
            Object temp = em.createNativeQuery("SELECT id FROM user u WHERE u.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public List<User> getUsersByType(UserType type) {
        Query query = null;
        try {
            EntityManager em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("userType"), type));
            query = em.createQuery(cr);
        } catch (Exception e){
            e.printStackTrace();
        }
        return query.getResultList();
    }

    public UserType getUserType(int id){
        EntityManager em = getEntityManager();
        Object temp = em.createNativeQuery("SELECT DTYPE FROM user u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        UserType rez = UserType.valueOf(temp.toString());
        return rez;
    }
}
