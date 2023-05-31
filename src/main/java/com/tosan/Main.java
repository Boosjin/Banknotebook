package com.tosan;

import com.tosan.dao.impl.FileProgressDaoImpl;
import com.tosan.service.CsvService;
import com.tosan.service.impl.CsvFileServiceImpl;
import com.tosan.service.impl.CsvServiceImpl;
import com.tosan.service.impl.FileProgressServiceImpl;
import com.tosan.service.impl.FileReaderServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        CsvService csvService = new CsvServiceImpl(
                new CsvFileServiceImpl(new FileReaderServiceImpl()),
                new FileProgressServiceImpl(new FileProgressDaoImpl()),
                new FileReaderServiceImpl());
        csvService.beginSavingProgress();
    }
}