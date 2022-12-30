package src.validator;

import java.util.regex.*;

public class Validator {
    public static boolean validMail(String input) {
        Pattern mailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mailCheck = mailPattern.matcher(input);

        if(mailCheck.matches()) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public static boolean validPhone(String input) {
        Pattern phonePattern = Pattern.compile("^[1-9]{1}+[0-9]{8}");
        Matcher phoneCheck = phonePattern.matcher(input);

        if(phoneCheck.matches()) {
            return true;
        }
        else {
            return false;
        }
    }
}