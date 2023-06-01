package com.tosan.service.impl.validator;

import java.util.regex.Pattern;

public abstract class NameValidatorService {
    public static boolean isNameValid(String name) {
        final Pattern pattern = Pattern.compile("[a-z,A-Z]{1,100}");
        return pattern.matcher(name).matches();
    }
}
