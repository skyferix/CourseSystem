package CourseWork.helpers;

import CourseWork.ds.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbUtils {

    public static Connection connect(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost/kursinis";
            String USER = "root";
            String PASS ="";
            conn = DriverManager.getConnection(DB_URL, USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void disconnect(Connection connection, Statement statement){
        if(connection!=null && statement != null){
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
