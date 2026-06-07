package com.minibanco.minibanco.service;

import com.minibanco.minibanco.dto.DepositoDTO;
import com.minibanco.minibanco.model.Conta;
import com.minibanco.minibanco.repository.ContaRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class ContaService {

    // Injeção de Dependência: O Service precisa do Repository para salvar os dados
    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    // Regra de Negócio: Criar uma nova conta com saldo inicial zero
    public Conta criarConta(Conta novaConta) {
        novaConta.setSaldo(BigDecimal.ZERO); // Toda a conta começa com 0€/R$
        return contaRepository.save(novaConta); // Salva no Postgres do Docker
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));
    }

    @org.springframework.transaction.annotation.Transactional // Garante que se o banco falhar no meio, nada se perde
    public Conta depositar(Long id, DepositoDTO dadosDeposito) {

        // 1. Validação de Valor Real (Maior que Zero)
        if (dadosDeposito.getValor() == null || dadosDeposito.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Fraude Recusada: O valor do depósito deve ser maior que zero!");
        }

        // 2. Simulação do Motor Anti-Fraude e Caminho da Transação
        System.out.println("LOG SEGURANÇA: Analisando rota de depósito vinda de: " + dadosDeposito.getOrigemTransacao());
        if ("ORIGEM_SUSPEITA".equals(dadosDeposito.getOrigemTransacao())) {
            throw new RuntimeException("ALERTA ANTI-FRAUDE: Bloqueado! Origem identificada como golpe conhecida.");
        }

        // 3. Simulação de Duplicidade (Idempotência)
        System.out.println("LOG SEGURANÇA: Validando Token contra duplicados: " + dadosDeposito.getTokenIdempotencia());
        // Em um sistema real, checaríamos se esse token já foi usado no banco de dados nas últimas 24h

        // 4. Execução segura se passar em todas as barreiras
        Conta conta = buscarPorId(id);
        BigDecimal novoSaldo = conta.getSaldo().add(dadosDeposito.getValor());
        conta.setSaldo(novoSaldo);

        return contaRepository.save(conta); // Salva o novo saldo atualizado no Docker
    }
    @org.springframework.transaction.annotation.Transactional
    public Conta sacar(Long id, BigDecimal valor) {

        // 1. Validação Básica
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Erro: O valor do saque deve ser maior que zero!");
        }

        Conta conta = buscarPorId(id);

        // 2. Regra de Ouro: O cliente tem dinheiro suficiente?
        // compareTo retorna -1 se o primeiro valor for menor que o segundo
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Operação Negada: Saldo insuficiente!");
        }

        // 3. Execução segura
        BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
        conta.setSaldo(novoSaldo);

        return contaRepository.save(conta); // Atualiza o saldo no banco
    }
}