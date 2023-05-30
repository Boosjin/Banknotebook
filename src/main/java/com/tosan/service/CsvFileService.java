package com.tosan.service;

import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;

public interface CsvFileService {
    public String getCsvFileUrl();

    String[] getCsvFileHeaders();

    FileReader getCsvFileFileReader(String csvFileUrl);

    public Iterable<CSVRecord> getCsvFileRecordsIterable(Reader reader, String[] csvHeaders);
}
