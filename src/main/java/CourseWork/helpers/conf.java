package CourseWork.helpers;

import CourseWork.fxControllers.Login;
import CourseWork.hibernateControllers.CourseHibControl;
import CourseWork.hibernateControllers.HibControl;
import CourseWork.hibernateControllers.UserHibControl;

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
