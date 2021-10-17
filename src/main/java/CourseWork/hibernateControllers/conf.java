package CourseWork.hibernateControllers;

import CourseWork.fxControllers.Login;

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

    //Session
    public static Preferences session = Preferences.userNodeForPackage(Login.class);

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
