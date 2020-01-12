package com.example.killerapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private String countryName;
    public Integer countryId;

    @Override
    public String toString() {
        return this.countryName;
    }
}
