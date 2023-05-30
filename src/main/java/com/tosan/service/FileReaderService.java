package com.tosan.service;

import java.io.FileReader;
import java.util.Iterator;

public interface FileReaderService {
    FileReader getFileReader(String fileUrl);
    boolean isFileEmpty(String fileUrl, int linesToSkip);
    FileReader skipCharacters(FileReader fileReader, long amount);
    Iterator<String> getFileIterator(String fileUrl);
}
