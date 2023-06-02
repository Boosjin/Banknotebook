package com.tosan.service;

import com.tosan.entity.FileProgress;

import java.util.Set;

public interface FileProgressService {
    void saveFileProgress(FileProgress fileProgress);

    FileProgress getFileProgress(String fileUrl);

    boolean isFileCompletelyProcessed(String fileUrl);

    Set<Long> getProcessedRecordsNumbers(String fileUrl);

    void markFileAsProcessed(String fileUrl);
}
