package org.apollo.template.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    // valid email patteren.
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@easv365\\.dk$";

    /**
     * Method for validating emails, and ensureing that they have the right suffix.
     * @param email String
     * @return boolean
     */
    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
