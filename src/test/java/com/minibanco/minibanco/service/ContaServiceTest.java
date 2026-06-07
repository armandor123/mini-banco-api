package com.minibanco.minibanco.service;

import com.minibanco.minibanco.dto.TransferenciaDTO;
import com.minibanco.minibanco.model.Conta;
import com.minibanco.minibanco.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Liga o Mockito (Os Dublês)
public class ContaServiceTest {

    @Mock // Cria um banco de dados de mentira (Dublê)
    private ContaRepository contaRepository;

    @InjectMocks // Injeta o banco de mentira dentro do nosso Service real
    private ContaService contaService;

    @Test
    void deveLancarExcecaoQuandoTentarSacarSemSaldo() {
        // 1. ARRANGE (Preparar o cenário)
        Long idConta = 1L;
        Conta contaFalsa = new Conta();
        contaFalsa.setId(idConta);
        contaFalsa.setSaldo(new BigDecimal("50.00")); // Cliente só tem 50 reais

        // Treina o dublê: "Quando o Service procurar a conta 1, devolve a contaFalsa"
        Mockito.when(contaRepository.findById(idConta)).thenReturn(Optional.of(contaFalsa));

        // 2 & 3. ACT & ASSERT (Agir e Verificar)
        // Vamos tentar sacar 100 reais e garantir que o Java dispara exatamente a Exceção que criamos
        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            contaService.sacar(idConta, new BigDecimal("100.00"));
        });

        // Verifica se a mensagem de erro que o sistema devolveu é a correta
        assertEquals("Operação Negada: Saldo insuficiente!", excecao.getMessage());
    }
    @Test
    void deveLancarExcecaoQuandoTransferirParaMesmaConta() {
        // 1. ARRANGE (Preparar os dados do PIX com IDs iguais)
        TransferenciaDTO pixInvalido = new TransferenciaDTO();
        pixInvalido.setIdContaOrigem(1L);
        pixInvalido.setIdContaDestino(1L); // Forçando o erro: Origem e Destino são a conta 1
        pixInvalido.setValor(new BigDecimal("100.00"));

        // 2 & 3. ACT & ASSERT (Agir e Verificar se o sistema barra)
        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            contaService.transferir(pixInvalido);
        });

        // Verifica se a barreira anti-fraude do PIX respondeu com a frase exata
        assertEquals("Operação Negada: Não pode transferir para si mesmo!", excecao.getMessage());
    }
}
