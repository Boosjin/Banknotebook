package com.tosan.service.impl;

import com.tosan.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Slf4j
public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public FileReader getFileReader(String fileUrl) {

        if (fileUrl == null) {
            log.info("You Can Not Provide A Null String As File Location\n\n\n");
            System.exit(0);
        }

        if (fileUrl.trim().equals("")) {
            log.info("You Can Not Provide An Empty String As File Location\n\n\n");
            System.exit(0);
        }

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileUrl);
        } catch (FileNotFoundException e) {
            log.info("File Was Not Found\nProvided Location: " + fileUrl + "\n\n\n");
            System.exit(0);
        }
        return fileReader;
    }
}
