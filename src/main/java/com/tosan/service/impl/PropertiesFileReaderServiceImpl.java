package com.tosan.service.impl;

import com.tosan.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public abstract class PropertiesFileReaderServiceImpl {

    private static Properties properties = null;

    private PropertiesFileReaderServiceImpl() {
    }

    public static Properties getPropertiesFile() {

        if (properties == null) {
            final FileReaderService fileReaderService = new FileReaderServiceImpl();
            FileReader fileReader = fileReaderService.getFileReader("src/main/resources/application.properties");
            properties = new Properties();
            try {
                properties.load(fileReader);
            } catch (IOException e) {
                log.info("Something Went Wrong While Loading Properties File");
                System.exit(0);
            }
        }
        return properties;
    }
}
