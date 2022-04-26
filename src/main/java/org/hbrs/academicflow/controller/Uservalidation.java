package org.hbrs.academicflow.controller;

import org.hbrs.academicflow.model.user.User;

public class Uservalidation {
    private Uservalidation(){

    }
    public static boolean isValidNewUser(User u){
        //Todo: Throw exception here
        String mail = u.getEmail();
        if (mail==null) {
            return false;
        }
        if (isNotValidMailAddress(mail)) {
            return false;
        }
        return emailNotAlreadyInUse(mail);
    }

    private static boolean emailNotAlreadyInUse(String mail) {
        return mail !=null;
        //TODO: add db check
    }

    private static boolean isNotValidMailAddress(String mail) {
        return mail.equals("abc");
    }
}
