package com.example.automatedmanager.dao;

import com.example.automatedmanager.model.Employment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EmploymentDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public EmploymentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Employment employment) {
        sessionFactory.getCurrentSession().persist(employment);
    }
}
