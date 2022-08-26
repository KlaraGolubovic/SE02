package org.hbrs.academicflow.util;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

  public static final String CURRENT_USER = "current_User";

  public static List<String> organizationOnlyRefs() {
    ArrayList<String> res = new ArrayList<>();
    res.add(Pages.ORGANIZATION_LIST_ADS);
    res.add(Pages.ORGANIZATION_CREATE_AD);
    res.add(Pages.ORGANIZATION_PROFILE_VIEW);
    res.add(Pages.ORGANIZATION_PROFILE_EDIT_VIEW);
    return res;
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Pages {

    public static final String MAIN_VIEW = "";
    public static final String LOGIN_VIEW = "login";
    public static final String WELCOME_VIEW = "welcome";
    public static final String REGISTER_VIEW = "register";
    public static final String ACCOUNT_VIEW = "account";
    public static final String ORGANIZATION_LIST_ADS = "organizationads";
    public static final String ORGANIZATION_CREATE_AD = "organizationnewad";
    public static final String ORGANIZATION_PROFILE_VIEW = "organizationprofile";
    public static final String ORGANIZATION_PROFILE_STUDENT_VIEW = "organizationprofileStudentView";
    public static final String ORGANIZATION_PROFILE_EDIT_VIEW = "editorganizationprofile";
    public static final String ORGANIZATION_EDIT_AD = "editad";
    public static final String ADAPPLICATION = "adapplication";
    public static final String STUDENT_PROFILE_VIEW = "studentprofile";
    public static final String STUDENT_LIST_ADS = "advertisements";
    public static final String STUDENT_APPLY_AD = "apply";
    public static final String STUDENT_PROFILE_EDIT_VIEW = "editstudentprofile";
    public static final String STUDENT_APPLICATION_VIEW = "myapplications";
    public static final String STUDENT_APPLICATION_VISIBLE = "deine-bewerbung";
    public static final String LANDING_VIEW = "landing";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Roles {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Errors {

    public static final String NO_USER_FOUND = "nouser";
    public static final String SQL_ERROR = "sql";
    public static final String DATABASE = "database";
  }
}
