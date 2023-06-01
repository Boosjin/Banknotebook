package com.tosan.service.impl.validator;

import java.util.regex.Pattern;

public abstract class NationalIdValidatorService {
    public static boolean isNationalIdValid(String nationalId) {
        final Pattern pattern = Pattern.compile("\\d{10}");
        return pattern.matcher(nationalId).matches();
    }
}
