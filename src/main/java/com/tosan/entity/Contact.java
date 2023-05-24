package com.tosan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "CONTACT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@DiscriminatorColumn(name = "CONTACT_TYPE", discriminatorType = DiscriminatorType.STRING)
@IdClass(ContactCompositeKey.class)
public abstract class Contact {

    @Id
    @Column(name = "NATIONAL_ID")
    @NotBlank(message = "You Must Provide A National ID")
    @Size(min = 10, max = 10, message = "The Length Of National ID MUST Be 10 Characters")
    private String nationalId;

    @Id
    @Column(name = "CONTACT")
    @NotBlank(message = "You Must Provide A Value For Contact")
    private String contactValue;
}

@Getter
@Setter
@EqualsAndHashCode
class ContactCompositeKey implements Serializable {
    private String nationalId;
    private String contactValue;
}