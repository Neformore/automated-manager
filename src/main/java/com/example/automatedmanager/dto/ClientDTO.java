package com.example.automatedmanager.dto;

import com.example.automatedmanager.model.Address;
import com.example.automatedmanager.model.Passport;
import lombok.Data;

@Data
public class ClientDTO {

    private int id;

    private String firstName;

    private String secondName;

    private String thirdName;

    private String telephoneNumber;

    private Address address;

    private Passport passport;
}
