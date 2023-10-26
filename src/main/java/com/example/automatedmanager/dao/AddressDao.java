package com.example.automatedmanager.dao;


import com.example.automatedmanager.model.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AddressDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public AddressDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Address address) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(address);
    }

    @Transactional(readOnly = true)
    public Optional<Address> findAddress(Address address) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select a from Address a where " +
                "a.countryName =: countryName " +
                "and a.cityName =: cityName " +
                "and a.streetName =: streetName " +
                "and a.houseNumber =: houseNumber", Address.class)
                .setParameter("countryName", address.getCountryName())
                .setParameter("cityName", address.getCityName())
                .setParameter("streetName", address.getStreetName())
                .setParameter("houseNumber", address.getHouseNumber())
                .stream().findAny();
    }
}
