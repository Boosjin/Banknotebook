package com.tosan.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public final class HomeNumber extends Contact {
    public HomeNumber(String nationalId, String homeNumber) {
        super(nationalId, homeNumber);
    }
}
