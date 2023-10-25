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

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @NotNull
    private Address address;

    @OneToOne(mappedBy = "client")
    private Passport passport;

    @OneToOne
    @JoinColumn(name = "employment_id", referencedColumnName = "id")
    @NotNull
    private Employment employmentId;

    @OneToMany(mappedBy = "client")
    private List<CreditContract> creditContracts;

    @OneToMany(mappedBy = "client")
    private List<CreditStatement> creditStatements;
}
