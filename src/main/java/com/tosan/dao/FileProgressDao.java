package com.tosan.dao;

import com.tosan.entity.FileProgress;

public interface FileProgressDao {
    void saveFileProgress(FileProgress fileProgress);
    FileProgress getFileProgress(String fileUrl);
}