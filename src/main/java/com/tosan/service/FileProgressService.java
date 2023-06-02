package com.tosan.service;

import com.tosan.entity.FileProgress;

import java.util.List;

public interface FileProgressService {
    void saveFileProgress(FileProgress fileProgress);
    FileProgress getFileProgress(String fileUrl);
    boolean isFileCompletelyProcessed(String fileUrl);
    long getNumberOfProcessedCharacters(String fileUrl);
    List<Long> getProcessedRecordsNumbers(String fileUrl);
}
