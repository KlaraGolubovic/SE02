package org.hbrs.academicflow.control;

import java.util.regex.Pattern;

public final class UserValidation {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  private UserValidation() {
    // Explicitly for sonar
  }

  public static boolean isValidMailAddress(String mail) {
    return EMAIL_PATTERN.matcher(mail).find();
  }

  public static boolean isNotValidMailAddress(String mail) {
    return !isValidMailAddress(mail);
  }

  // A method that checks if a string is a valid username
  public static boolean isValidUsername(String username) {
    if (username.length() >= 3 && username.length() <= 20) {
      return username.matches("^[a-zA-Z0-9_]*$");
    }
    return false;
  }

  public static boolean isNotValidUsername(String username) {
    return !isValidUsername(username);
  }
}
