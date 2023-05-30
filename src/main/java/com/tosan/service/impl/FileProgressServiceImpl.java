package com.tosan.service.impl;

import com.tosan.dao.FileProgressDao;
import com.tosan.entity.FileProgress;
import com.tosan.entity.ProcessedCharactersRange;
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
    public long getNumberOfProcessedCharacters(String fileUrl) {
        final FileProgress fileProgress = this.getFileProgress(fileUrl);
        if (fileProgress == null) return 0;
        else return fileProgress.getNumberOfProcessedCharacters();
    }

    @Override
    public List<Long> getProcessedCharactersRanges(String fileUrl) {
        // getting and sorting ranges
        List<Long> ranges = new LinkedList<>();
        final FileProgress fileProgress = this.getFileProgress(fileUrl);
        if (fileProgress == null) return ranges;
        final List<ProcessedCharactersRange> processedCharactersRanges = fileProgress.getProcessedCharactersRanges();
        if (processedCharactersRanges == null || processedCharactersRanges.size() == 0) return ranges;
        processedCharactersRanges.forEach(processedCharactersRange ->
        {
            ranges.add(processedCharactersRange.getBeginning());
            ranges.add(processedCharactersRange.getEnd());
        });
        if (ranges.size() > 2) {
            // remove neighbour ranges
            Collections.sort(ranges);
            List<Long> mergedRanges = new ArrayList<>();
            mergedRanges.add(ranges.get(0));
            for (int i = 1; i < ranges.size() - 1; i += 2) {
                if (ranges.get(i) + 1 == ranges.get(i + 1)) continue;
                mergedRanges.add(ranges.get(i));
                mergedRanges.add(ranges.get(i + 1));
            }
            mergedRanges.add(ranges.get(ranges.size() - 1));
            return mergedRanges;
        }
        return ranges;
    }
}
