package com.tosan.service.impl;

import com.tosan.service.CsvFileService;
import com.tosan.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

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
        return csvFileUrl.trim();
    }

    @Override
    public String[] getCsvFileHeaders() {
        final String csvHeadersString = PropertiesFileReaderServiceImpl
                .getPropertiesFile()
                .getProperty("csvHeaders");
        if (csvHeadersString == null) {
            log.info("The Property Called \"csvHeaders\" Was Not Found In Properties File\n\n\n");
            System.exit(0);
        }
        String[] csvHeaders = csvHeadersString.split(",");
        String[] csvTrimmedHeaders = new String[csvHeaders.length];
        for (int i = 0; i < csvHeaders.length; i++) {
            if (csvHeaders[i].trim().equals("")) {
                log.info("You Can Not Provide An Empty String As CSV Header\n\n\n");
                System.exit(0);
            }
            csvTrimmedHeaders[i] = csvHeaders[i].trim();
        }
        return csvTrimmedHeaders;
    }

    @Override
    public FileReader getCsvFileFileReader(String csvFileUrl) {
        if (csvFileUrl == null || csvFileUrl.trim().equals("")) {
            log.info("You Can Not Provide An Empty Or Null String As CSV File Url\n\n\n");
            System.exit(0);
        }
        return fileReaderService.getFileReader(csvFileUrl.trim());
    }

    @Override
    public CSVParser getCsvFileParser(Reader reader, String[] csvHeaders) {
        if (reader == null) {
            log.info("You Can Not Provide A Null Reader For Getting CSV File Parser\n\n\n");
            System.exit(0);
        }
        if (csvHeaders == null) {
            log.info("You Can Not Provide A Null Array As CSV Headers For Getting CSV File Parser\n\n\n");
            System.exit(0);
        }
        CSVParser csvParser = null;
        try {
            csvParser = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(csvHeaders)
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);
        } catch (IOException e) {
            log.info("Something Went Wrong While Parsing CSV File\n\n\n");
            System.exit(0);
        }
        return csvParser;
    }
}
