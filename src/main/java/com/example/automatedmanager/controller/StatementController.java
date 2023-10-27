package com.example.automatedmanager.controller;

import com.example.automatedmanager.dto.ClientDTO;
import com.example.automatedmanager.dto.CreditContractDTO;
import com.example.automatedmanager.dto.StatementDTO;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.CreditContract;
import com.example.automatedmanager.service.ClientService;
import com.example.automatedmanager.service.CreditStatementService;
import com.example.automatedmanager.util.StatementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/statement")
public class StatementController {

    private final ClientService clientService;
    private final CreditStatementService creditStatementService;
    private final StatementValidator statementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public StatementController(ClientService clientService, CreditStatementService creditStatementService, StatementValidator statementValidator, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.creditStatementService = creditStatementService;
        this.statementValidator = statementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getStatementCreaturePage(@ModelAttribute("statement") StatementDTO statementDTO) {
        return "statement/statement-creature";
    }

    @PostMapping
    public String createStatement(@ModelAttribute("statement") @Valid StatementDTO statementDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        // первая проверка на синтаксическое содержание формы (пустые поля, соответствие regex и тд)
        if (bindingResult.hasErrors()) {
            return "statement/statement-creature";
        }
        // вторая проверка на валидность полученных значений (повторения с записями в бд)
        statementValidator.validate(statementDTO , bindingResult);
        if (bindingResult.hasErrors()) {
            return "statement/statement-creature";
        }

        Client client = clientService.getClient(statementDTO);
        CreditContract creditContract = creditStatementService.approval(client, statementDTO.getAmountMoney());
        if (creditContract != null) {
            CreditContractDTO creditContractDTO = new CreditContractDTO(creditContract.getAmountMoney(),
                    creditContract.getAmountDays());

            model.addAttribute("creditContractDTO", creditContractDTO);
            model.addAttribute("clientDTO", convertToClientDTO(client));
            return "statement/signature-page";
        }

        return "statement/refusal-page.html";
    }

    private ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
}
