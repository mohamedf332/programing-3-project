package util;

import java.util.regex.Pattern;

public class Validator {

    // ===== Student =====
    public static boolean isValidName(String name) {
        return name != null && Pattern.matches("^[a-zA-Zأ-ي ]{3,50}$", name);
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && Pattern.matches("^01[0-2,5]{1}[0-9]{8}$", phone);
    }

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }

    // ===== Course =====
    public static boolean isValidCourseName(String name) {
        return name != null && Pattern.matches("^[a-zA-Zأ-ي0-9 ]{3,50}$", name);
    }

    public static boolean isValidDuration(String duration) {
        return duration != null && Pattern.matches(
                "^[0-9]{1,2}\\s*(day|days|week|weeks|month|months)$",
                duration.toLowerCase());
    }

    public static boolean isValidFees(int fees) {
        return fees > 0 && fees <= 100000;
    }
}
