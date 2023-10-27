package com.example.automatedmanager.util;

import com.example.automatedmanager.dao.AddressDao;
import com.example.automatedmanager.dao.ClientDao;
import com.example.automatedmanager.dao.PassportDao;
import com.example.automatedmanager.dto.StatementDTO;
import com.example.automatedmanager.model.Address;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.Passport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class StatementValidator implements Validator {

    private final ClientDao clientDao;
    private final PassportDao passportDao;
    private final AddressDao addressDao;

    @Autowired
    public StatementValidator(ClientDao clientDao, PassportDao passportDao, AddressDao addressDao) {
        this.clientDao = clientDao;
        this.passportDao = passportDao;
        this.addressDao = addressDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StatementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StatementDTO statementDTO = (StatementDTO) target;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startWork = format.parse(statementDTO.getStartWork());
            Date endWork = format.parse(statementDTO.getEndWork());
            if (startWork.getTime() > endWork.getTime()) {
                errors.rejectValue("startWork", "", "Дата начала рабочего контракта не может быть больше даты его окончания");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // проверяем пользователя по ФИО, если значения полей сущности, вернувшейся из
        // БД полностью не совпадают со значениями формы, то записываем ошибку
        if (!checkByFio(statementDTO)) {
            errors.rejectValue("firstName", "", "Ошибка, " +
                    "пользователь с таким ФИО уже существует, " +
                    "но у него были другие значения ключевых полей (ФИО, паспортные данные, прописка, место работы), " +
                    "пожалуйста проверте правильность вводимых данных и попробуйте еще раз");
        }
        // аналогично с ФИО
        if (!checkByTelephone(statementDTO)) {
            errors.rejectValue("telephoneNumber", "", "Ошибка, " +
                    "пользователь с таким номером телефона уже существует, " +
                    "но у него были другие значения ключевых полей (ФИО, паспортные данные, прописка, место работы), " +
                    "пожалуйста проверте правильность вводимых данных и попробуйте еще раз");
        }
        // аналогично с ФИО
        if (!checkByPassport(statementDTO)) {
            errors.rejectValue("passportSeries", "", "Ошибка, " +
                    "пользователь с такими паспортными данными уже существует, " +
                    "но у него были другие значения ключевых полей (ФИО, паспортные данные, прописка, место работы), " +
                    "пожалуйста проверте правильность вводимых данных и попробуйте еще раз");
        }
        if (!checkByAddress(statementDTO)) {
            errors.rejectValue("countryName", "", "Ошибка, " +
                    "пользователь с указанными ФИО и паспортными данными живет по другой прописке, " +
                    "пожалуйста проверте правильность вводимых данных и попробуйте еще раз");
        }
    }

    // Метод находит пользователя по ФИО в БД и проверяет равенство введенных полей, в случае если пользователь не найден - вернет true
    private boolean checkByFio(StatementDTO statementDTO) {
        Optional<Client> clientByFio = clientDao.findClientByFio(statementDTO.getFirstName(),
                statementDTO.getSecondName(),
                statementDTO.getThirdName());

        return clientByFio.map(client -> checkFields(client, statementDTO)).orElse(true);
    }

    // Метод находит пользователя по номеру телефона в БД и проверяет равенство введенных полей, в случае если пользователь не найден - вернет true
    private boolean checkByTelephone(StatementDTO statementDTO) {
        Optional<Client> clientByTelephone = clientDao.findClientByTelephone(statementDTO.getTelephoneNumber());

        return clientByTelephone.map(client -> checkFields(client, statementDTO)).orElse(true);
    }

    // Метод находит пользователя по паспорту в БД и проверяет равенство введенных полей, в случае если пользователь не найден - вернет true
    private boolean checkByPassport(StatementDTO statementDTO) {
        Optional<Passport> passport = passportDao.findPassport(Integer.parseInt(statementDTO.getPassportSeries()),
                Integer.parseInt(statementDTO.getPassportNumber()));

        return passport.map(value -> checkFields(value.getClient(), statementDTO)).orElse(true);
    }

    // Метод находит пользователя по прописке в БД и проверяет равенство введенных полей, в случае если пользователь не найден - вернет true
    private boolean checkByAddress(StatementDTO statementDTO) {
        Address address = new Address(statementDTO.getCountryName(),
                statementDTO.getCityName(),
                statementDTO.getStreetName(),
                statementDTO.getHouseNumber());

        Optional<Address> addressOptional = addressDao.findAddress(address);
        Optional<Client> client = clientDao.findClientByTelephone(statementDTO.getTelephoneNumber());

        // если пользователя нет в БД вернем true
        if (client.isEmpty())
            return true;

        // если пользователь полученный по прописке не равен пользователю полученному по номеру телефона
        // значит новая прописка уникальна, вернем true
        if (addressOptional.isPresent()) {
            if (addressOptional.get().getClient().getId() == client.get().getId()) {
                // если пользователи совпали значит нужно проверить равенство их прописки
                // в случае если прописки у пользователя разные то вернем ошибку
                return addressOptional.map(value -> checkFields(value.getClient(), statementDTO)).orElse(true);
            } else return true; // клиенты не совпали значит правильность адресов проверять не нужно
        } else return true; //адрес не найден
    }

    // метод проверяет равенство полей клиента с полями полученными из формы
    private boolean checkFields(Client client, StatementDTO statementDTO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startWork = format.parse(statementDTO.getStartWork());
            Date endWork = format.parse(statementDTO.getEndWork());
            return client.getTelephoneNumber().equals(statementDTO.getTelephoneNumber()) &&
                    client.getFamilyStatus() == statementDTO.getFamilyStatus() &&

                    client.getPassport().getPassportSeries() == Integer.parseInt(statementDTO.getPassportSeries()) &&
                    client.getPassport().getPassportNumber() == Integer.parseInt((statementDTO.getPassportNumber())) &&

                    client.getEmployment().getOrganizationName().equals(statementDTO.getOrganizationName()) &&
                    client.getEmployment().getJobTitle().equals(statementDTO.getJobTitle()) &&
                    client.getEmployment().getStartWork().getTime() == startWork.getTime() &&
                    client.getEmployment().getEndWork().getTime() == endWork.getTime() &&

                    client.getAddress().getCountryName().equals(statementDTO.getCountryName()) &&
                    client.getAddress().getCityName().equals(statementDTO.getCityName()) &&
                    client.getAddress().getStreetName().equals(statementDTO.getStreetName()) &&
                    client.getAddress().getHouseNumber().equals(statementDTO.getHouseNumber());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
