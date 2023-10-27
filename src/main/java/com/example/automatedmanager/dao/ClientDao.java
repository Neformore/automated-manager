package com.example.automatedmanager.dao;

import com.example.automatedmanager.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ClientDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(client);
    }

    @Transactional(readOnly = true)
    public List<Client> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cl from  Client cl", Client.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Client> findClientByFio(String firstName, String secondName, String thirdName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cl from Client cl where " +
                        "cl.firstName =: firstName " +
                        "and cl.secondName =: secondName " +
                        "and cl.thirdName =: thirdName", Client.class)
                .setParameter("firstName", firstName)
                .setParameter("secondName", secondName)
                .setParameter("thirdName", thirdName)
                .stream().findAny();
    }

    @Transactional
    public Optional<Client> findClientByTelephone(String telephoneNumber) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select cl from Client cl where cl.telephoneNumber =: telephoneNumber", Client.class)
                .setParameter("telephoneNumber", telephoneNumber)
                .stream().findAny();
    }

    @Transactional
    public Client findById(int clientId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Client.class, clientId);
    }
}
