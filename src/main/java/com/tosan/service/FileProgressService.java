package com.tosan.service;

import com.tosan.entity.FileProgress;

public interface FileProgressService {

    void saveFileProgress(FileProgress fileProgress);

    FileProgress getFileProgress(String fileUrl);

    boolean isFileCompletelyProcessed(FileProgress fileProgress);
}
