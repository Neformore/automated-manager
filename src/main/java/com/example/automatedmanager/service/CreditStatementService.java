package com.example.automatedmanager.service;

import com.example.automatedmanager.dao.CreditStatementDao;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.CreditContract;
import com.example.automatedmanager.model.CreditStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CreditStatementService {

    private final CreditStatementDao creditStatementDao;

    @Autowired
    public CreditStatementService(CreditStatementDao creditStatementDao) {
        this.creditStatementDao = creditStatementDao;
    }

    public List<CreditStatement> getCreditStatements() {
        return creditStatementDao.index();
    }

    public CreditContract approval(Client client, int amountMoney) {
        Random random = new Random();

        CreditStatement creditStatement = new CreditStatement(true, client);

        creditStatementDao.save(creditStatement);

        if (creditStatement.isStatus()) {
            CreditContract creditContract = new CreditContract();

            creditContract.setAmountMoney(amountMoney);
            creditContract.setAmountDays(random.nextInt(365 - 30 + 1) + 30); // диапазон от 30 до 365 дней (1 - 12 мес.)
            return creditContract;
        }
        return null;
    }
}
