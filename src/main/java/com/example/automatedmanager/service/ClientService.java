package com.example.automatedmanager.service;

import com.example.automatedmanager.dao.AddressDao;
import com.example.automatedmanager.dao.ClientDao;
import com.example.automatedmanager.dao.EmploymentDao;
import com.example.automatedmanager.dao.PassportDao;
import com.example.automatedmanager.dto.StatementDTO;
import com.example.automatedmanager.model.Address;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.Employment;
import com.example.automatedmanager.model.Passport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientDao clientDao;
    private final ModelMapper modelMapper;
    private final AddressDao addressDao;
    private final EmploymentDao employmentDao;
    private final PassportDao passportDao;

    @Autowired
    public ClientService(ClientDao clientDao, ModelMapper modelMapper, AddressDao addressDao, EmploymentDao employmentDao, PassportDao passportDao) {
        this.clientDao = clientDao;
        this.modelMapper = modelMapper;
        this.addressDao = addressDao;
        this.employmentDao = employmentDao;
        this.passportDao = passportDao;
    }


    // возвращает пользователя из БД, если тот найден, или создает и возвращет нового
    public Client getClient(StatementDTO statementDTO) {
        Client client = checkClient(statementDTO);
        if (client != null) {
            return client;
        } else {
            client = convertToClient(statementDTO);
            clientDao.save(client);

            Address address = convertToAddress(statementDTO);
            address.setClient(client);
            addressDao.save(address);

            Employment employment = converToEmployment(statementDTO);
            employment.setClient(client);
            employmentDao.save(employment);

            Passport passport = convertToPassport(statementDTO);
            passport.setClient(client);
            passportDao.save(passport);

            return client;
        }
    }

    public Client checkClient(StatementDTO statementDTO) {
        Optional<Client> client = clientDao.findClientByFio(statementDTO.getFirstName(),
                statementDTO.getSecondName(),
                statementDTO.getThirdName());
        return client.orElse(null);
    }

    private Client convertToClient(StatementDTO statementDTO) {
        return modelMapper.map(statementDTO, Client.class);
    }

    private Address convertToAddress(StatementDTO statementDTO) {
        return modelMapper.map(statementDTO, Address.class);
    }

    private Employment converToEmployment(StatementDTO statementDTO) {
        return modelMapper.map(statementDTO, Employment.class);
    }

    private Passport convertToPassport(StatementDTO statementDTO) {
        return modelMapper.map(statementDTO, Passport.class);
    }
}
