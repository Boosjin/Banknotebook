package com.tosan.service.impl.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;

@Slf4j
public abstract class PersonCsvRecordValidatorService {
    public static boolean isCsvRecordValid(CSVRecord record, String[] csvHeaders) {
        if (record.size() != csvHeaders.length) {
            log.info("Invalid Number Of Arguments In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        if (!GenericValidator.isDate(record.get("dateOfBirth").trim(), "yyyy-MM-dd", false)) {
            log.info("Invalid Date Of Birth In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        if (!NationalIdValidatorService.isNationalIdValid(record.get("nationalId").trim())) {
            log.info("Invalid National Id In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        if (!NameValidatorService.isNameValid(record.get("firstName").trim())) {
            log.info("Invalid First Name In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        if (!NameValidatorService.isNameValid(record.get("lastName").trim())) {
            log.info("Invalid Last Name In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        final String email = record.get("email").trim();
        final String phoneNumber = record.get("mobileNumber").trim();
        final String homeNumber = record.get("homeNumber").trim();
        if (email.length() == 0 && phoneNumber.length() == 0 && homeNumber.length() == 0) {
            log.info("At Least One Contact Must Be Provided. Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        if (!email.equals("") && !EmailValidator.getInstance().isValid(email)) {
            log.info("Invalid Email In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        if (!phoneNumber.equals("") && !ContactNumberValidatorService.isContactNumberValid(phoneNumber)) {
            log.info("Invalid Phone Number In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        if (!homeNumber.equals("") && !ContactNumberValidatorService.isContactNumberValid(homeNumber)) {
            log.info("Invalid Home Number In Record Number " + record.getRecordNumber() + "\n\n\n");
            return false;
        }
        return true;
    }
}
