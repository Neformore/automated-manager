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
    @Pattern(regexp = "[А-Я][а-я]+", message = "Введено некорректное имя")
    private String firstName;

    @Column(name = "second_name")
    @Pattern(regexp = "[А-Я][а-я]+", message = "Введена некорректная фамилия")
    private String secondName;

    @Column(name = "third_name")
    @Pattern(regexp = "[А-Я][а-я]+", message = "Введено некорректное отчество")
    private String thirdName;

    @Column(name = "telephone_number")
    @Pattern(regexp = "9[0-9]{9}", message = "Введен некорректный номер телефона")
    private String telephoneNumber;

    @Column(name = "family_status")
    @NotNull
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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", familyStatus=" + familyStatus +
                ", address=" + address +
                ", passport=" + passport +
                ", employment=" + employment +
                ", creditContracts=" + creditContracts +
                ", creditStatements=" + creditStatements +
                '}';
    }
}
