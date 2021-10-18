package CourseWork.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {

    public static String hashPwd(String password){
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");

            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            for (byte b: resultByteArray) {
                sb.append(String.format("%02x", b));
            }

        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return sb.toString();
    }
}
