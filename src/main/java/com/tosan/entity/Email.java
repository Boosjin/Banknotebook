package com.tosan.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class Email extends Contact {
    public Email(
            @Size(min = 10, max = 10, message = "The Length Of National ID MUST Be 10 Characters")
            String nationalId,
            @jakarta.validation.constraints.Email(message = "You Must Provide A Valid Email")
            String email) {
        super(nationalId, email);
    }
}
