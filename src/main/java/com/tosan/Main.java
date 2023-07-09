package com.tosan;

import com.tosan.dao.impl.FileProgressDaoImpl;
import com.tosan.dao.impl.PersonDaoImpl;
import com.tosan.service.CsvService;
import com.tosan.service.impl.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // TODO add comments
        // TODO put saving people an processed record numbers in one transaction
        // TODO correct log levels
        // TODO add validator for file url
        CsvService csvService = new CsvServiceImpl(
                new CsvFileServiceImpl(new FileReaderServiceImpl()),
                new FileProgressServiceImpl(new FileProgressDaoImpl()),
                new FileReaderServiceImpl(),
                new PersonServiceImpl(new PersonDaoImpl()));
        csvService.fileCompletionCheck();
        csvService.emptyFileCheck();
        csvService.beginSavingProgress();
    }
}