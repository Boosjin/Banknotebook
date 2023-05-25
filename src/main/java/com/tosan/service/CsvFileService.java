package com.tosan.service;

import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;

public interface CsvFileService {

    public String getCsvFileUrl();

    String[] getCsvFileHeaders();

    FileReader getCsvFileFileReader(String csvFileUrl);

    Iterable<CSVRecord> getCsvFileRecordsIterable(FileReader csvFileReader, String[] csvHeaders);

}
