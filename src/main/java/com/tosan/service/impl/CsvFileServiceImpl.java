package com.tosan.service.impl;

import com.tosan.service.CsvFileService;
import com.tosan.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class CsvFileServiceImpl implements CsvFileService {

    private final FileReaderService fileReaderService;

    public CsvFileServiceImpl(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @Override
    public String getCsvFileUrl() {
        final String csvFileUrl = PropertiesFileReaderServiceImpl
                .getPropertiesFile()
                .getProperty("csvFileUrl");
        if (csvFileUrl == null) {
            log.info("The Property Called \"csvFileUrl\" Was Not Found In Properties File\n\n\n");
            System.exit(0);
        }
        return csvFileUrl;
    }

    @Override
    public String[] getCsvFileHeaders() {
        final String csvHeaders = PropertiesFileReaderServiceImpl
                .getPropertiesFile()
                .getProperty("csvHeaders");
        if (csvHeaders == null) {
            log.info("The Property Called \"csvHeaders\" Was Not Found In Properties File\n\n\n");
            System.exit(0);
        }
        return csvHeaders.split(",");
    }

    @Override
    public FileReader getCsvFileFileReader(String csvFileUrl) {
        if (csvFileUrl == null || csvFileUrl.trim().equals("")) {
            log.info("You Can Not Provide An Empty Or Null String As CSV File Url\n\n\n");
            System.exit(0);
        }
        return fileReaderService.getFileReader(csvFileUrl);
    }

    @Override
    public Iterable<CSVRecord> getCsvFileRecordsIterable(FileReader csvFileReader, String[] csvHeaders) {
        Iterable<CSVRecord> csvRecordIterable = null;
        try {
            csvRecordIterable = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(csvHeaders)
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(csvFileReader);
        } catch (IOException e) {
            log.info("Something Went Wrong While Parsing CSV File\n\n\n");
            System.exit(0);
        }
        return csvRecordIterable;
    }
}
