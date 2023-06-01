package com.tosan.service.impl.validator;

import java.util.regex.Pattern;

public abstract class ContactNumberValidatorService {
    public static boolean isContactNumberValid(String contactNumber) {
        final Pattern pattern = Pattern.compile("0\\d{10}");
        return pattern.matcher(contactNumber).matches();
    }
}
