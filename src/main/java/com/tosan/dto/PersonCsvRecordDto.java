package com.tosan.dto;

import com.tosan.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonCsvRecordDto {
    private Person person;
    private Long csvRecordNumber;
}
