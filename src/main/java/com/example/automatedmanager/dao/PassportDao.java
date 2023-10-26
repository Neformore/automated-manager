package com.example.automatedmanager.dao;

import com.example.automatedmanager.model.Passport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class PassportDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public PassportDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Passport passport) {
        sessionFactory.getCurrentSession().persist(passport);
    }

    @Transactional(readOnly = true)
    public Optional<Passport> findPassport(int series, int number) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Passport p where p.passportSeries =: series and p.passportNumber =: number", Passport.class)
                .setParameter("series", series)
                .setParameter("number", number)
                .stream().findAny();
    }
}
