package com.example.automatedmanager.controller;

import com.example.automatedmanager.dto.StatementDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/statement")
public class StatementController {

    @GetMapping
    public String getStatementCreaturePage(@ModelAttribute("statement") StatementDTO statementDTO) {
        return "statement-creature";
    }

    @PostMapping
    public String createStatement(@ModelAttribute("statement") StatementDTO statementDTO) {
        System.out.println();
        return "redirect:/statement";
    }
}
