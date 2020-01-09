package com.example.killerapp.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Address {
    public String streetName;
    public String streetNumber;
    public String postNumber;
    public String city;
    public String country;
    public Integer countryId;
}
