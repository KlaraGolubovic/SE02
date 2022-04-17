package org.hbrs.academicflow.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String CURRENT_USER = "current_User";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Pages {
        public static final String MAIN_VIEW = "";
        public static final String LOGIN_VIEW = "login";
        public static final String WELCOME_VIEW = "welcome";
        public static final String REGISTER_VIEW = "register";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Roles {
        public static final String ADMIN = "admin";
        public static final String USER = "user";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Errors {
        public static final String NO_USER_FOUND = "nouser";
        public static final String SQL_ERROR = "sql";
        public static final String DATABASE = "database";
    }
}
