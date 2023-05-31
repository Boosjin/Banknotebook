package com.tosan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "PROCESSED_LINES_RANGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@IdClass(ProcessedLinesRangeCompositeKey.class)
public class ProcessedLinesRange {

    @Id
    @Column(name = "FILE_URL")
    @NotBlank(message = "File URL Can Not Be An Empty String")
    private String fileUrl;

    @Id
    @Column(name = "BEGINNING")
    @NotNull(message = "The Beginning Of The Range Can Not Be Null")
    @PositiveOrZero(message = "The Beginning Of The Range Can Not Be A Negative Number")
    private Integer beginning;

    @Id
    @Column(name = "END")
    @NotNull(message = "The End Of The Range Can Not Be Null")
    @PositiveOrZero(message = "The End Of The Range Can Not Be A Negative Number")
    private Integer end;
}

@Getter
@Setter
@EqualsAndHashCode
class ProcessedLinesRangeCompositeKey implements Serializable {
    private String fileUrl;
    private Long beginning;
    private Long end;
}