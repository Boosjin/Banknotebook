package com.tosan.service.impl;

import com.tosan.service.FileReaderService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

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
            fileReader = new FileReader(fileUrl.trim());
        } catch (FileNotFoundException e) {
            log.info("File Was Not Found\nProvided Location: " + fileUrl.trim() + "\n\n\n");
            System.exit(0);
        }
        return fileReader;
    }

    @Override
    public boolean isFileEmpty(String fileUrl, int linesToSkip) {
        if (fileUrl == null) {
            log.info("You Can Not Provide A Null String As File URL\n\n\n");
            System.exit(0);
        }
        if (fileUrl.trim().equals("")) {
            log.info("You Can Not Provide An Empty String As File URL\n\n\n");
            System.exit(0);
        }
        if (linesToSkip < 0) {
            log.info("The Number Of Lines To Skip Can Not Be Negative\n\n\n");
            System.exit(0);
        }
        try (BufferedReader bufferedReader = new BufferedReader(this.getFileReader(fileUrl.trim()))) {
            for (int i = 0; i < linesToSkip + 1; i++) {
                if (bufferedReader.readLine() == null) return true;
            }
        } catch (IOException e) {
            log.info("Something Went Wrong While Reading The Lines From The File\n\tFile URL: " + fileUrl.trim() + "\n\n\n");
            System.exit(0);
        }
        return false;
    }

    @Override
    public FileReader skipCharacters(FileReader fileReader, long amount) {
        try {
            fileReader.skip(amount);
        } catch (IOException e) {
            log.info("Something Went Wrong While Skipping Characters In File");
            System.exit(0);
        }
        return fileReader;
    }

    @Override
    public Iterator<String> getFileIterator(String fileUrl) {
        FileReader fileReader = this.getFileReader(fileUrl);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        return bufferedReader.lines().iterator();
    }
}
