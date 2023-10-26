package com.example.automatedmanager.util;

import com.example.automatedmanager.dto.StatementDTO;
import com.example.automatedmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class StatementValidator implements Validator {

    private final ClientService clientService;

    @Autowired
    public StatementValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StatementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StatementDTO statementDTO = (StatementDTO) target;
        checkedFio(statementDTO);
    }

    private boolean checkedFio(StatementDTO statementDTO) {
        return true;
    }
}
