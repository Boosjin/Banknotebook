package com.tosan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "PROCESSED_CHARACTERS_RANGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@IdClass(ProcessedCharactersRangeCompositeKey.class)
public class ProcessedCharactersRange {

    @Id
    @Column(name = "FILE_URL")
    @NotBlank(message = "File URL Can Not Be An Empty String")
    private String fileUrl;

    @Id
    @Column(name = "BEGINNING")
    @NotNull(message = "The Beginning Of The Range Can Not Be Null")
    @PositiveOrZero(message = "The Beginning Of The Range Can Not Be A Negative Number")
    private Long beginning;

    @Id
    @Column(name = "END")
    @NotNull(message = "The End Of The Range Can Not Be Null")
    @PositiveOrZero(message = "The End Of The Range Can Not Be A Negative Number")
    private Long end;
}

@Getter
@Setter
@EqualsAndHashCode
class ProcessedCharactersRangeCompositeKey implements Serializable {
    private String fileUrl;
    private Long beginning;
    private Long end;
}