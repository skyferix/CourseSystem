package CourseWork.helpers;

import CourseWork.fxControllers.Login;
import CourseWork.hibernateControllers.CourseHibControl;
import CourseWork.hibernateControllers.HibControl;
import CourseWork.hibernateControllers.PersonHibControl;
import CourseWork.hibernateControllers.UserHibControl;
import org.hibernate.bytecode.enhance.internal.tracker.SimpleCollectionTracker;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

public class conf {
    //Entity manager
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSystemMng");
    public static UserHibControl userHibControl = new UserHibControl(entityManagerFactory);
    public static CourseHibControl courseHibControl = new CourseHibControl(entityManagerFactory);
    public static HibControl hibControl = new HibControl(entityManagerFactory);
    public static PersonHibControl personHibControl = new PersonHibControl(entityManagerFactory);

    //Session
    public static Preferences session = Preferences.userNodeForPackage(Login.class);

    public static Integer getUserId() {
        return Integer.parseInt(session.get("userId","def"));
    }
    public static void putUserId(int userId) {
        session.put("userId", String.valueOf(userId));
        System.out.println(session.get("userId", "def"));
    }
    public static void resetUserId() {
        session.remove("userId");
    }

    //others
    public static Date parseDate(String date)  {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
