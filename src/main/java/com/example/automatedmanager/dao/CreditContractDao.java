package com.example.automatedmanager.dao;

import com.example.automatedmanager.model.CreditContract;
import com.example.automatedmanager.model.CreditStatement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<CreditContract> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from  CreditContract c", CreditContract.class).getResultList();
    }
}
