package com.tosan.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String nationalId;

    @Id
    @Column(name = "CONTACT_VALUE")
    private String contactValue;
}

@Getter
@Setter
@EqualsAndHashCode
class ContactCompositeKey implements Serializable {
    private String nationalId;
    private String contactValue;
}