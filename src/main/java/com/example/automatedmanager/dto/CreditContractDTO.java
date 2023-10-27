package com.example.automatedmanager.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreditContractDTO {

    private int amountMoney;

    private int amountDays;

    private Date dateOfSigning;

    private boolean status;

    public CreditContractDTO(int amountMoney, int amountDays) {
        this.amountMoney = amountMoney;
        this.amountDays = amountDays;
    }
}
