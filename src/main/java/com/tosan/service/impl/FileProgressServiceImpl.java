package com.tosan.service.impl;

import com.tosan.dao.FileProgressDao;
import com.tosan.entity.FileProgress;
import com.tosan.entity.ProcessedRecordNumber;
import com.tosan.service.FileProgressService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    public boolean isFileCompletelyProcessed(String fileUrl) {
        final FileProgress fileProgress = this.getFileProgress(fileUrl);
        if (fileProgress == null) return false;
        else return fileProgress.getFinished();
    }

    @Override
    public List<Long> getProcessedRecordsNumbers(String fileUrl) {
        final List<Long> list = new LinkedList<>();
        final FileProgress fileProgress = this.getFileProgress(fileUrl);
        if (fileProgress == null) return list;
        final List<ProcessedRecordNumber> processedRecordsNumbers = fileProgress.getProcessedRecordsNumbers();
        if (processedRecordsNumbers.size() == 0) return list;
        processedRecordsNumbers.forEach(processedRecordNumber -> list.add(processedRecordNumber.getRecordNumber()));
        if (list.size() == 1) return list;
        Collections.sort(list);
        return list;
    }

    @Override
    public void markFileAsProcessed(String fileUrl) {
        FileProgress fileProgress = this.getFileProgress(fileUrl);
        if (fileProgress == null) {
            fileProgress = new FileProgress(
                    fileUrl,
                    0,
                    0,
                    true,
                    new ArrayList<>());
            this.saveFileProgress(fileProgress);
        } else {
            fileProgress.setFinished(true);
            this.saveFileProgress(fileProgress);
        }
    }
}
