package com.example.automatedmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Data
public class Address implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private String houseNumber;

    public Address(String countryName, String cityName, String streetName, String houseNumber) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }
}
