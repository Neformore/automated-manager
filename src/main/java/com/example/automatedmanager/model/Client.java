package com.example.automatedmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "client")
@NoArgsConstructor
@Data
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "third_name")
    private String thirdName;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "family_status")
    private Boolean familyStatus;

    @OneToOne(mappedBy = "client")
    private Address address;

    @OneToOne(mappedBy = "client")
    private Passport passport;

    @OneToOne(mappedBy = "client")
    private Employment employment;

    @OneToMany(mappedBy = "client")
    private List<CreditContract> creditContracts;

    @OneToMany(mappedBy = "client")
    private List<CreditStatement> creditStatements;
}
