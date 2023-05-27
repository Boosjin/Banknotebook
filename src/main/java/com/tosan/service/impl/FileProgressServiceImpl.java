package com.tosan.service.impl;

import com.tosan.dao.FileProgressDao;
import com.tosan.entity.FileProgress;
import com.tosan.service.FileProgressService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProgressServiceImpl implements FileProgressService {

    private final FileProgressDao fileProgressDao;

    public FileProgressServiceImpl(FileProgressDao fileProgressDao) {
        this.fileProgressDao = fileProgressDao;
    }

    @Override
    public void saveFileProgress(FileProgress fileProgress) {
        if (fileProgress == null) {
            log.info("You Can Not Provide A Null File Progress To Save");
            System.exit(0);
        }
        fileProgressDao.saveFileProgress(fileProgress);
    }

    @Override
    public FileProgress getFileProgress(String fileUrl) {
        if (fileUrl == null) {
            log.info("You Can Not Pass A Null String As File Location\n\n\n");
            System.exit(0);
        }
        if (fileUrl.trim().equals("")) {
            log.info("You Can Not Pass An Empty String As File Location\n\n\n");
            System.exit(0);
        }
        return fileProgressDao.getFileProgress(fileUrl);
    }

    @Override
    public boolean isFileCompletelyProcessed(FileProgress fileProgress) {
        if (fileProgress == null) return false;
        return fileProgress.getFinished();
    }
}
