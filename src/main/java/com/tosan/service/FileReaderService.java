package com.tosan.service;

import java.io.FileReader;

public interface FileReaderService {

    FileReader getFileReader(String fileUrl);

    boolean isFileEmpty(String fileUrl, int linesToSkip);
}
