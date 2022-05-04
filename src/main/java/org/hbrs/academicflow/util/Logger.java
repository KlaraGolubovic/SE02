package org.hbrs.academicflow.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"java:S106"})
public class Logger {

  public static void noUserGiven() {
    System.out.println("LOG: In Constructor of AppView - No User given!");
  }

  public static void userLoggedInMessage(String alias) {
    System.out.println("LOG: Showing AppView logged in as " + alias);
  }
}
