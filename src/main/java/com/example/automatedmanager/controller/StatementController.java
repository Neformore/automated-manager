package com.example.automatedmanager.controller;

import com.example.automatedmanager.dto.StatementDTO;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.service.ClientService;
import com.example.automatedmanager.service.CreditStatementService;
import com.example.automatedmanager.util.StatementValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/statement")
public class StatementController {

    private final ClientService clientService;
    private final CreditStatementService creditStatementService;
    private final StatementValidator statementValidator;

    @Autowired
    public StatementController(ClientService clientService, CreditStatementService creditStatementService, StatementValidator statementValidator) {
        this.clientService = clientService;
        this.creditStatementService = creditStatementService;
        this.statementValidator = statementValidator;
    }

    @GetMapping
    public String getStatementCreaturePage(@ModelAttribute("statement") StatementDTO statementDTO) {
        return "statement-creature";
    }

    @PostMapping
    public String createStatement(@ModelAttribute("statement") @Valid StatementDTO statementDTO, BindingResult bindingResult) {
        statementValidator.validate(statementDTO , bindingResult);

        if (bindingResult.hasErrors()) {
            return "statement-creature";
        }

        Client client = clientService.getClient(statementDTO);
        System.out.println();
        return "redirect:/statement";
    }
}
