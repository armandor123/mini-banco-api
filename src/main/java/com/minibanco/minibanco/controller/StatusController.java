package com.minibanco.minibanco.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/status")
    public String checkStatus() {
        return "Mini-Banco está online e operante!";
    }
}
