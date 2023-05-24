package com.tosan.service.impl;

import com.tosan.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertiesFileReaderServiceImpl {

    private final FileReaderService fileReaderService;

    public PropertiesFileReaderServiceImpl(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    public Properties getPropertiesFile() {
        FileReader fileReader = fileReaderService.getFileReader("application.properties");
        final Properties properties = new Properties();
        try {
            properties.load(fileReader);
            return properties;
        } catch (IOException e) {
            log.info("Something Went Wrong While Loading Properties File");
            throw new RuntimeException(e);
        }
    }
}
