package com.example.killerapp.models;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    public Integer id;
    public String name; //name and surname
    public String type;
    public String registrationNumber;
    public String email;
    public String phoneNumber;
    public String taxNumber;
    public Boolean taxPayer;
    public Address address;
    public Integer countryId;
}
