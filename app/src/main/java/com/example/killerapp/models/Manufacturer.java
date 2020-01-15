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
public class Manufacturer {
    private Integer manufacturerId;
    private String manufacturerName;
    private String manufacturerStreetName;
    private String manufacturerStreetNumber;
    private String manufacturerPostNumber;
    private String manufacturerCity;
    private String countryId;
    private Country country;
    private String manufacturerEmail;
    private String manufacturerPhoneNumber;
}
