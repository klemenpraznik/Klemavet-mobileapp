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
public class Client {
    public Integer id;
    public String name; //name and surname
    public String type;
    public String registrationNumber;
    public String email;
    public String phoneNumber;
    public String taxNumber;
    public Boolean taxPayer;
    public String streetName;
    public String streetNumber;
    public String postNumber;
    public String city;
    public Country country;
    public Integer countryId;
}
