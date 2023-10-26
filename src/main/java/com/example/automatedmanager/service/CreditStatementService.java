package com.example.automatedmanager.service;

import com.example.automatedmanager.dao.CreditStatementDao;
import com.example.automatedmanager.dto.StatementDTO;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.CreditStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CreditStatementService {

    private final CreditStatementDao creditStatementDao;
    private final ClientService clientService;

    @Autowired
    public CreditStatementService(CreditStatementDao creditStatementDao, ClientService clientService) {
        this.creditStatementDao = creditStatementDao;
        this.clientService = clientService;
    }

    public void approval(StatementDTO statementDTO) {
//        CreditStatement creditStatement = new CreditStatement(new Random().nextBoolean(), client);
//        creditStatementDao.save(creditStatement);
    }
}
