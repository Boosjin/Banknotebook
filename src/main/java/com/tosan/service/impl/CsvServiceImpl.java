package com.tosan.service.impl;

import com.tosan.assembler.CsvRecordToPersonObjectAssembler;
import com.tosan.dto.PersonCsvRecordDto;
import com.tosan.entity.FileProgress;
import com.tosan.entity.Person;
import com.tosan.entity.ProcessedRecordNumber;
import com.tosan.service.*;
import com.tosan.service.impl.validator.PersonCsvRecordValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Slf4j
public final class CsvServiceImpl implements CsvService {

    private final CsvFileService csvFileService;
    private final FileProgressService fileProgressService;
    private final FileReaderService fileReaderService;
    private final PersonService personService;
    private final String fileUrl;

    public CsvServiceImpl(CsvFileService csvFileService, FileProgressService fileProgressService, FileReaderService fileReaderService, PersonService personService) {
        this.csvFileService = csvFileService;
        this.fileProgressService = fileProgressService;
        this.fileReaderService = fileReaderService;
        this.personService = personService;
        fileUrl = csvFileService.getCsvFileUrl();
    }

    @Override
    public void fileCompletionCheck() {
        if (fileProgressService.isFileCompletelyProcessed(fileUrl)) {
            log.info("File Had Been Processed Before\nFile URL: " + fileUrl + "\n\n\n");
            System.exit(0);
        }
    }

    @Override
    public void emptyFileCheck() {
        FileProgress fileProgress = fileProgressService.getFileProgress(fileUrl);
        // if there was no file progress check for empty file
        if (fileProgress == null) {
            // first line in csv file is headers
            if (fileReaderService.isFileEmpty(fileUrl, 1)) {
                log.info("File is Empty\nFile URL: " + fileUrl + "\n\n\n");
                markFileAsProcessed();
            }
        }
    }

    @Override
    public void markFileAsProcessed() {
        fileProgressService.markFileAsProcessed(fileUrl);
        System.exit(0);
    }

    @Override
    public void beginSavingProgress() {
        final String[] csvHeaders = csvFileService.getCsvFileHeaders();
        Stream<PersonCsvRecordDto> personCsvRecordDtoStream = getCsvRecordsStream()
                .filter(record -> {
                    // check if the record is valid
                    if (!PersonCsvRecordValidatorService.isCsvRecordValid(record, csvHeaders)) {
                        invalidRecordSubmitter(record.getRecordNumber());
                        return false;
                    } else return true;
                })
                .map(record -> new PersonCsvRecordDto(CsvRecordToPersonObjectAssembler.assemble(record), record.getRecordNumber()));
        partitionAndSavePeople(personCsvRecordDtoStream);
    }

    private Stream<CSVRecord> getCsvRecordsStream() {
        // returning a stream of csv records and skip the previously processed ones if there is any
        final FileReader csvFileFileReader = csvFileService.getCsvFileFileReader(fileUrl);
        final String[] csvHeaders = csvFileService.getCsvFileHeaders();
        final CSVParser csvFileParser = csvFileService.getCsvFileParser(csvFileFileReader, csvHeaders);
        final Set<Long> processedRecordsNumbers = fileProgressService.getProcessedRecordsNumbers(fileUrl);
        Stream<CSVRecord> streamOfRecords;
        // skip the previously processed records if there is any
        if (processedRecordsNumbers.size() > 0) {
            streamOfRecords = csvFileParser
                    .stream()
                    .filter(record -> !processedRecordsNumbers.contains(record.getRecordNumber()));
        } else {
            streamOfRecords = csvFileParser
                    .stream();
        }
        return streamOfRecords;
    }

    private void invalidRecordSubmitter(long recordNumber) {
        FileProgress fileProgress = fileProgressService.getFileProgress(fileUrl);
        ProcessedRecordNumber processedRecordNumber = new ProcessedRecordNumber(fileUrl, recordNumber);
        if (fileProgress == null) {
            Set<ProcessedRecordNumber> processedRecordNumbers = new HashSet<>();
            processedRecordNumbers.add(processedRecordNumber);
            fileProgress = new FileProgress(
                    fileUrl,
                    0,
                    1,
                    false,
                    processedRecordNumbers
            );
        } else {
            fileProgress.addProcessedRecordNumber(processedRecordNumber);
            fileProgress = new FileProgress(
                    fileProgress.getFileUrl(),
                    fileProgress.getValidRecords(),
                    fileProgress.getInvalidRecords() + 1,
                    fileProgress.getFinished(),
                    fileProgress.getProcessedRecordsNumbers()
            );
        }
        fileProgressService.saveFileProgress(fileProgress);
    }

    private int getPeopleBatchSize() {
        Properties properties = PropertiesFileReaderServiceImpl.getPropertiesFile();
        int peopleBatchSize = Integer.parseInt(properties.getProperty("peopleBatchSize"));
        return Math.max(peopleBatchSize, 1);
    }

    private void partitionAndSavePeople(Stream<PersonCsvRecordDto> personCsvRecordDtoStream) {
        final int batchSize = getPeopleBatchSize();
        final ThreadLocal<List<Person>> people = ThreadLocal.withInitial(LinkedList::new);
        final ThreadLocal<Set<Long>> recordNumbers = ThreadLocal.withInitial(HashSet::new);
        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        final Future<?> future = executorService.submit(() -> personCsvRecordDtoStream
                .parallel()
                .forEach(personCsvRecordDto -> {
                    if (people.get().size() < batchSize - 1) {
                        people.get().add(personCsvRecordDto.getPerson());
                        recordNumbers.get().add(personCsvRecordDto.getCsvRecordNumber());
                    } else {
                        people.get().add(personCsvRecordDto.getPerson());
                        recordNumbers.get().add(personCsvRecordDto.getCsvRecordNumber());
                        savePeople(people.get(), recordNumbers.get());
                        people.get().clear();
                        recordNumbers.get().clear();
                    }
                }));
        executorService.shutdown();
        while (true) {
            try {
                if (future.isDone()) {
                    if (people.get().size() > 0) savePeople(people.get(), recordNumbers.get());
                    markFileAsProcessed();
                    break;
                } else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log.info("Main Thread Was Interrupted\n\n\n");
                System.exit(0);
            }
        }
    }

    private void savePeople(List<Person> people, Set<Long> recordNumbers) {
        FileProgress fileProgress = fileProgressService.getFileProgress(fileUrl);
        if (fileProgress == null) {
            fileProgress = new FileProgress(
                    fileUrl,
                    0,
                    0,
                    false,
                    new HashSet<>()
            );
        }
        final Set<ProcessedRecordNumber> processedRecordNumbers = fileProgress.getProcessedRecordsNumbers();
        recordNumbers.forEach(processedRecordsNumber -> {
            processedRecordNumbers.add(new ProcessedRecordNumber(fileUrl, processedRecordsNumber));
        });
        fileProgress.setProcessedRecordsNumbers(processedRecordNumbers);
        fileProgress.setValidRecords(fileProgress.getValidRecords() + people.size());
        personService.save(people);
        fileProgressService.saveFileProgress(fileProgress);
    }
}