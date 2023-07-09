package com.tosan.service;

public interface CsvService {
    void fileCompletionCheck();
    void emptyFileCheck();
    void markFileAsProcessed();
    void beginSavingProgress();
}
