package com.tosan.service.impl;

import com.tosan.entity.Person;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public abstract class PersonValidator {
    private static final Validator VALIDATOR;

    static {
        try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            VALIDATOR = validatorFactory.getValidator();
        }
    }

    public static boolean isPersonValid(Person person) {
        Set<ConstraintViolation<Person>> personConstraintViolations = VALIDATOR.validate(person);
        return personConstraintViolations.isEmpty();
    }
}
