package com.example.automatedmanager.dao;

import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.CreditStatement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CreditStatementDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public CreditStatementDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<CreditStatement> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cs from  CreditStatement cs", CreditStatement.class).getResultList();
    }

    @Transactional
    public void save(CreditStatement creditStatement) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(creditStatement);
    }
}
