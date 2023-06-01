package com.tosan.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class Email extends Contact {
    public Email(String nationalId, String email) {
        super(nationalId, email);
    }
}
