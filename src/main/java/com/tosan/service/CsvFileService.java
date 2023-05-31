package com.tosan.service;

import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.io.Reader;

public interface CsvFileService {
    String getCsvFileUrl();

    String[] getCsvFileHeaders();

    FileReader getCsvFileFileReader(String csvFileUrl);

    CSVParser getCsvParser(Reader reader, String[] csvHeaders);
}
