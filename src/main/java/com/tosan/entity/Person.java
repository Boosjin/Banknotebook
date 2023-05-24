package com.tosan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "PERSON")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Person {

    @Id
    @Column(name = "NATIONAL_ID")
    @NotBlank(message = "National ID Can Not Be An Empty String")
    @Size(min = 10, max = 10, message = "National ID Must Be 10 Characters")
    private String nationalId;

    @Column(name = "FIRST_NAME")
    @NotBlank(message = "Name Can Not Be An Empty String")
    @Size(max = 100, message = "Maximum Length Of First Name Is 100 Characters")
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotBlank(message = "Last Name Can Not Be An Empty String")
    @Size(max = 100, message = "Maximum Length Of Last Name Is 100 Characters")
    private String lastName;

    @Column(name = "DATE_OF_BIRTH")
    @NotNull(message = "You Must Provide Date Of Birth")
    @PastOrPresent(message = "Date Of Birth Can Not Be In The Future")
    private LocalDate DateOfBirth;
}
