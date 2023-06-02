package com.tosan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FILE_PROGRESS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FileProgress {

    @Id
    @Column(name = "FILE_URL")
    private String fileUrl;

    @Column(name = "VALID_RECORDS")
    private Integer validRecords;

    @Column(name = "INVALID_RECORDS")
    private Integer invalidRecords;

    @Column(name = "FINISHED")
    private Boolean finished;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FILE_URL")
    private Set<ProcessedRecordNumber> processedRecordsNumbers;

    public void setProcessedRecordsNumbers(Set<ProcessedRecordNumber> processedRecordsNumbers) {
        this.processedRecordsNumbers = processedRecordsNumbers;
        processedRecordsNumbers.forEach(processedRecordNumber -> processedRecordNumber.setFileUrl(this.getFileUrl()));
    }

    public void addProcessedRecordNumber(ProcessedRecordNumber processedRecordNumber) {
        if (this.processedRecordsNumbers == null) this.processedRecordsNumbers = new HashSet<>();
        processedRecordNumber.setFileUrl(this.getFileUrl());
        this.processedRecordsNumbers.add(processedRecordNumber);
    }
}
