package com.tosan.service.impl;

import com.tosan.dao.ProcessedRecordNumberDao;
import com.tosan.entity.ProcessedRecordNumber;
import com.tosan.service.ProcessedRecordNumberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessedRecordNumberServiceImpl implements ProcessedRecordNumberService {

    private final ProcessedRecordNumberDao processedRecordNumberDao;

    public ProcessedRecordNumberServiceImpl(ProcessedRecordNumberDao processedRecordNumberDao) {
        this.processedRecordNumberDao = processedRecordNumberDao;
    }

    @Override
    public void saveProcessedRecordNumber(ProcessedRecordNumber processedRecordNumber) {
        if (processedRecordNumber == null) {
            log.info("Null Processed Record Number Can Not Be Saved In Database\n\n\n");
            System.exit(0);
        }
        if (processedRecordNumber.getRecordNumber() == null || processedRecordNumber.getRecordNumber() < 1) {
            log.info("Invalid Processed Record Number\n\n\n");
            System.exit(0);
        }
        if (processedRecordNumber.getFileUrl() == null || processedRecordNumber.getFileUrl().trim().equals("")) {
            log.info("Invalid Processed Record File Url\n\n\n");
            System.exit(0);
        }
        processedRecordNumberDao.saveProcessedRecordNumber(processedRecordNumber);
    }
}
