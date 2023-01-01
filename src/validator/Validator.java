package src.validator;

import java.util.regex.*;

public class Validator {
    public static boolean isValidMail(String mail) {
        Pattern mailPattern = Pattern.compile("[A-Za-z\\d._%+-]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,4}");
        Matcher mailCheck = mailPattern.matcher(mail);

        return mailCheck.matches();
    }

    public static boolean isValidPhone(String phone) {
        Pattern phonePattern = Pattern.compile("^[1-9]\\d{8}");
        Matcher phoneCheck = phonePattern.matcher(phone);

        return phoneCheck.matches();
    }
}
