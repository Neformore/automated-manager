package com.example.automatedmanager.dto;

import com.example.automatedmanager.model.Client;
import lombok.Data;

@Data
public class CreditStatementDTO {

    private boolean status;

    private Client client;
}
