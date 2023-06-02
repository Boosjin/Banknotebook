package com.tosan.assembler;

import com.tosan.entity.Email;
import com.tosan.entity.HomeNumber;
import com.tosan.entity.MobileNumber;
import com.tosan.entity.Person;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.util.ArrayList;

public class CsvRecordToPersonObjectAssembler {
    public static Person assemble(CSVRecord record) {
        final String nationalId = record.get("nationalId").trim();
        final Person person = new Person(nationalId,
                record.get("firstName").trim(),
                record.get("lastName").trim(),
                LocalDate.parse(record.get("dateOfBirth").trim()),
                new ArrayList<>());
        if (!record.get("email").trim().equals(""))
            person.addContactInfo(new Email(nationalId, record.get("email").trim()));
        if (!record.get("mobileNumber").trim().equals(""))
            person.addContactInfo(new MobileNumber(nationalId, record.get("mobileNumber").trim()));
        if (!record.get("homeNumber").trim().equals(""))
            person.addContactInfo(new HomeNumber(nationalId, record.get("homeNumber").trim()));
        return person;
    }
}
