package com.example.automatedmanager.dto;

import com.example.automatedmanager.model.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreditContractDTO {

    private int amountMoney;

    private int amountDays;

    private Date dateOfSigning;

    private boolean status;

    private Client client;

    public CreditContractDTO(int amountMoney, int amountDays) {
        this.amountMoney = amountMoney;
        this.amountDays = amountDays;
    }
}
