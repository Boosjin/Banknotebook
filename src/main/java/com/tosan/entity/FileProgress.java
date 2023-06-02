package com.tosan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

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
    @NotBlank(message = "File URL Can Not Be An Empty String")
    private String fileUrl;

    @Column(name = "PROCESSED_CHARACTERS")
    @PositiveOrZero(message = "Number Of Processed Characters Can Not Be Negative")
    private Long numberOfProcessedCharacters;

    @Column(name = "VALID_RECORDS")
    @PositiveOrZero(message = "Number Of Valid Records Can Not Be Negative")
    private Integer validRecords;

    @Column(name = "INVALID_RECORDS")
    @PositiveOrZero(message = "Number Of Invalid Records Can Not Be Negative")
    private Integer invalidRecords;

    @Column(name = "FINISHED")
    private Boolean finished;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fileUrl")
    private List<ProcessedRecordNumber> processedRecordsNumbers;
}
