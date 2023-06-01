package com.tosan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "PROCESSED_RECORD_NUMBER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@IdClass(ProcessedRecordNumberCompositeKey.class)
public class ProcessedRecordNumber {

    @Id
    @Column(name = "FILE_URL")
    @NotBlank(message = "File URL Can Not Be An Empty String")
    private String fileUrl;

    @Id
    @Column(name = "RECORD_NUMBER")
    @NotNull(message = "The Record Number Can Not Be Null")
    @PositiveOrZero(message = "The Record Number Can Not Be A Negative Number")
    private Long recordNumber;
}

@Getter
@Setter
@EqualsAndHashCode
class ProcessedRecordNumberCompositeKey implements Serializable {
    private String fileUrl;
    private Long recordNumber;
}