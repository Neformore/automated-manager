package com.example.automatedmanager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class StatementDTO {

    @Pattern(regexp = "[А-Я][а-я]+", message = "Введено некорректное имя")
    private String firstName;

    @Pattern(regexp = "[А-Я][а-я]+", message = "Введена некорректная фамилия")
    private String secondName;

    @Pattern(regexp = "[А-Я][а-я]+", message = "Введено некорректное отчество")
    private String thirdName;

    @Pattern(regexp = "[0-9]{4}", message = "Введена некорректная серия паспорта")
    private String passportSeries;

    @Pattern(regexp = "[0-9]{6}", message = "Введен некорректный номер паспорта")
    private String passportNumber;

    @NotNull
    private Boolean familyStatus;

    @NotEmpty
    private String countryName;

    @NotEmpty
    private String cityName;

    @NotEmpty
    private String streetName;

    @NotEmpty
    private String houseNumber;

    @Pattern(regexp = "9[0-9]{9}", message = "Введен некорректный номер телефона")
    private String telephoneNumber;

    @NotNull
    private String startWork;

    @NotNull
    private String stopWork;

    @NotEmpty
    private String jobTitle;

    @NotEmpty
    private String organizationName;

    @NotNull
    private Integer amountMoney;
}