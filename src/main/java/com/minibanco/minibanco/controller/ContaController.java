package com.minibanco.minibanco.controller;

import com.minibanco.minibanco.dto.TransferenciaDTO;
import com.minibanco.minibanco.model.Conta;
import com.minibanco.minibanco.service.ContaService;
import org.springframework.web.bind.annotation.*;
import com.minibanco.minibanco.dto.DepositoDTO;

@RestController
@RequestMapping("/contas") // Todas rotas desta Classe vão começar com /contas
public class ContaController {

    private final ContaService contaService;

    public  ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    //ROta para cirrar Conta( verbo POST)
    @PostMapping
    public Conta criar(@RequestBody Conta conta){
        return  contaService.criarConta(conta);

    }
    // Rota para buscar conta pelo ID (verbo GET)
    @GetMapping("/{id}")
    public Conta buscar(@PathVariable Long id){
        return  contaService.buscarPorId(id);
    }


    @PutMapping("/{id}/deposito")
    public Conta realizarDeposito(@PathVariable Long id, @RequestBody DepositoDTO dadosDeposito) {
        return contaService.depositar(id, dadosDeposito);
    }
    // Rota para Saque (Verbo PUT porque estamos a alterar um saldo existente)
    @PutMapping("/{id}/saque")
    public Conta realizarSaque(@PathVariable Long id, @RequestBody DepositoDTO dadosSaque) {
        // Estamos a reaproveitar o DTO do depósito apenas para extrair o valor que vem no JSON
        return contaService.sacar(id, dadosSaque.getValor());
    }
    @PostMapping("/transferir")
    public String realizarTransferencia(@RequestBody TransferenciaDTO dadosTransferencia) {
        contaService.transferir(dadosTransferencia);
        return "PIX realizado com sucesso!";
    }
}
