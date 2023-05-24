package com.tosan.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class MobileNumber extends Contact {
    public MobileNumber(
            @Size(min = 10, max = 10, message = "The Length Of National ID MUST Be 10 Characters")
            String nationalId,
            @Size(min = 11, max = 11, message = "Mobile Number Must Be 11 Digits")
            String mobileNumber) {
        super(nationalId, mobileNumber);
    }
}
