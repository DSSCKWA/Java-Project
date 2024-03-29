package src.validator;

import java.util.regex.*;

public class Validator {
    public static boolean isValidMail(String mail) {
        Pattern mailPattern = Pattern.compile("[A-Za-z\\d._%+-]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,4}");
        Matcher mailCheck = mailPattern.matcher(mail);

        return mailCheck.matches();
    }

    public static boolean isValidPhone(String phone) {
        Pattern phonePattern = Pattern.compile("^[1-9]\\d{8}$");
        Matcher phoneCheck = phonePattern.matcher(phone);

        return phoneCheck.matches();
    }

    public static boolean isValidString(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static boolean isValidStringWithSpace(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s]+$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static boolean isValidStringWithDash(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ-]+$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static boolean isValidStringWithDashAndSpace(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s-]+$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static boolean isValidAddress(String address) {
        String regex = "^((\\d+)\\s)?[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s]+(\\s\\d+)?\\s\\d+[a-zA-Z]?(/\\d+)?[a-zA-Z]?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        return matcher.matches();
    }
}
