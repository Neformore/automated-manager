package com.example.automatedmanager.dao;

import com.example.automatedmanager.model.CreditContract;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreditContractDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public CreditContractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(CreditContract creditContract) {
        sessionFactory.getCurrentSession().persist(creditContract);
    }
}
