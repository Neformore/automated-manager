package com.example.automatedmanager.service;

import com.example.automatedmanager.dao.CreditContractDao;
import com.example.automatedmanager.model.CreditContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditContractService {

    private final CreditContractDao creditContractDao;

    @Autowired
    public CreditContractService(CreditContractDao creditContractDao) {
        this.creditContractDao = creditContractDao;
    }

    public void saveContract(CreditContract creditContract) {
        creditContractDao.save(creditContract);
    }
}
