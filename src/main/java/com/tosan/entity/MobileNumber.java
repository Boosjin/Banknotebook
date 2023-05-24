package com.tosan.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class MobileNumber extends Contact {
    public MobileNumber(String nationalId, String mobileNumber) {
        super(nationalId, mobileNumber);
    }
}
