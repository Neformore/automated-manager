package com.example.automatedmanager.controller;

import com.example.automatedmanager.dto.CreditContractDTO;
import com.example.automatedmanager.model.Client;
import com.example.automatedmanager.model.CreditContract;
import com.example.automatedmanager.service.ClientService;
import com.example.automatedmanager.service.CreditContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/credit")
public class CreditController {

    private final ModelMapper modelMapper;
    private final ClientService clientService;
    private final CreditContractService creditContractService;

    @Autowired
    public CreditController(ModelMapper modelMapper, ClientService clientService, CreditContractService creditContractService) {
        this.modelMapper = modelMapper;
        this.clientService = clientService;
        this.creditContractService = creditContractService;
    }

    @PostMapping("/signed")
    public String signedContract(@ModelAttribute("creditContractDTO") CreditContractDTO creditContractDTO,
                                 @RequestParam int clientId) {
        creditContractDTO.setStatus(true);
        creditContractDTO.setDateOfSigning(new Date());
        Client client = clientService.getClient(clientId);
        convertAndSave(creditContractDTO, client);

        return "credit/success-page";
    }

    @PostMapping("/refusal")
    public String refusalContract(@ModelAttribute("creditContractDTO") CreditContractDTO creditContractDTO,
                                  @RequestParam int clientId) {
        creditContractDTO.setStatus(false);

        Client client = clientService.getClient(clientId);
        convertAndSave(creditContractDTO, client);

        return "credit/refusal-page";
    }

    private void convertAndSave(CreditContractDTO creditContractDTO, Client client) {
        CreditContract creditContract = convertToCreditContract(creditContractDTO);
        creditContract.setClient(client);
        creditContractService.saveContract(creditContract);
    }

    private CreditContract convertToCreditContract(CreditContractDTO creditContractDTO) {
        return modelMapper.map(creditContractDTO, CreditContract.class);
    }
}
