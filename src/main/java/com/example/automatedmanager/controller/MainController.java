package com.example.automatedmanager.controller;

import com.example.automatedmanager.dto.ClientDTO;
import com.example.automatedmanager.dto.CreditContractDTO;
import com.example.automatedmanager.dto.CreditStatementDTO;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.CreditContract;
import com.example.automatedmanager.model.CreditStatement;
import com.example.automatedmanager.model.Passport;
import com.example.automatedmanager.service.ClientService;
import com.example.automatedmanager.service.CreditContractService;
import com.example.automatedmanager.service.CreditStatementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/main")
public class MainController {

    private final ClientService clientService;
    private final CreditStatementService creditStatementService;
    private final CreditContractService creditContractService;
    private final ModelMapper modelMapper;

    @Autowired
    public MainController(ClientService clientService,
                          CreditStatementService creditStatementService,
                          CreditContractService creditContractService,
                          ModelMapper modelMapper) {
        this.clientService = clientService;
        this.creditStatementService = creditStatementService;
        this.creditContractService = creditContractService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("clients", clientService.getClients()
                .stream().map(this::convertToClientDTO));
        return "main/main-page";
    }

    @PostMapping("/find-client-by-telephone")
    public String findByTelephoneNumber(@RequestParam String telephoneNumber, Model model) {
        Optional<Client> clientOptional = clientService.getClient(telephoneNumber);
        if (clientOptional.isPresent()) {
            model.addAttribute("client", convertToClientDTO(clientOptional.get()));
            return "main/client-info";
        }
        return "errors/error-find-by-telephone.html";
    }

    @PostMapping("/find-client-by-fio")
    public String findClientByFio(@RequestParam String firstName,
                                  @RequestParam String secondName,
                                  @RequestParam String thirdName,
                                  Model model) {
        Optional<Client> clientOptional = clientService.getClient(firstName, secondName, thirdName);

        if (clientOptional.isPresent()) {
            model.addAttribute("client", convertToClientDTO(clientOptional.get()));
            return "main/client-info";
        }
        return "errors/error-find-by-fio.html";
    }

    @PostMapping("/find-client-passport-fio")
    public String findClientByPassport(@RequestParam(required = false) Integer series,
                                       @RequestParam(required = false) Integer number,
                                       Model model) {
        if(series == null || number == null)
            return "errors/error-find-by-passport.html";

        Optional<Passport> passportOptional = clientService.getPassport(series, number);

        if (passportOptional.isPresent()) {
            model.addAttribute("client", convertToClientDTO(passportOptional.get().getClient()));
            return "main/client-info";
        }
        return "errors/error-find-by-passport.html";
    }

    @PostMapping("/get-statements")
    public String getStatementsPage(Model model) {
        model.addAttribute("creditStatements", creditStatementService.getCreditStatements()
                .stream().map(this::convertToCreditStatementDTO));
        return "main/credit-statement-list.html";
    }

    @PostMapping("/get-contracts")
    public String getContractsPage(Model model) {
        model.addAttribute("creditContracts", creditContractService.getCreditContracts()
                .stream().map(this::convertToCreditContractDTO));
        return "main/credit-contracts-list.html";
    }

    private ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    private CreditStatementDTO convertToCreditStatementDTO(CreditStatement creditStatement) {
        return modelMapper.map(creditStatement, CreditStatementDTO.class);
    }

    private CreditContractDTO convertToCreditContractDTO(CreditContract creditContract) {
        return modelMapper.map(creditContract, CreditContractDTO.class);
    }
}
