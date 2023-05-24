package com.tosan.service.impl;

import com.tosan.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Slf4j
public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public FileReader getFileReader(String fileUrl) {
        try {
            return new FileReader(fileUrl);
        } catch (FileNotFoundException e) {
            log.info("File Was Not Found\nProvided Location: " + fileUrl);
            throw new RuntimeException(e);
        }
    }
}
