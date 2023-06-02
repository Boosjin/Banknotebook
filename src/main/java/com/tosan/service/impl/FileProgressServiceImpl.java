package com.tosan.service.impl;

import com.tosan.dao.FileProgressDao;
import com.tosan.entity.FileProgress;
import com.tosan.entity.ProcessedRecordNumber;
import com.tosan.service.FileProgressService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

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
        return fileProgressDao.getFileProgress(fileUrl.trim());
    }

    @Override
    public boolean isFileCompletelyProcessed(String fileUrl) {
        if (fileUrl == null) {
            log.info("You Can Not Pass A Null String As File Location\n\n\n");
            System.exit(0);
        }
        if (fileUrl.trim().equals("")) {
            log.info("You Can Not Pass An Empty String As File Location\n\n\n");
            System.exit(0);
        }
        final FileProgress fileProgress = this.getFileProgress(fileUrl.trim());
        if (fileProgress == null) return false;
        else return fileProgress.getFinished();
    }

    @Override
    public Set<Long> getProcessedRecordsNumbers(String fileUrl) {
        if (fileUrl == null) {
            log.info("You Can Not Pass A Null String As File Location\n\n\n");
            System.exit(0);
        }
        if (fileUrl.trim().equals("")) {
            log.info("You Can Not Pass An Empty String As File Location\n\n\n");
            System.exit(0);
        }
        final Set<Long> set = new HashSet<>();
        final FileProgress fileProgress = this.getFileProgress(fileUrl.trim());
        if (fileProgress == null) return set;
        final Set<ProcessedRecordNumber> processedRecordsNumbers = fileProgress.getProcessedRecordsNumbers();
        processedRecordsNumbers.forEach(processedRecordNumber -> set.add(processedRecordNumber.getRecordNumber()));
        return set;
    }

    @Override
    public void markFileAsProcessed(String fileUrl) {
        FileProgress fileProgress = this.getFileProgress(fileUrl.trim());
        if (fileProgress == null) {
            fileProgress = new FileProgress(
                    fileUrl.trim(),
                    0,
                    0,
                    true,
                    new HashSet<>());
            this.saveFileProgress(fileProgress);
        } else {
            fileProgress.setFinished(true);
            this.saveFileProgress(fileProgress);
        }
    }
}
