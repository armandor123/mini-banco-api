package com.minibanco.minibanco.exception;

import com.minibanco.minibanco.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Avisa o Spring: "Fica de olho em todos os Controllers!"
public class GlobalExceptionHandler {

    // Quando qualquer RuntimeException explodir no sistema, este método é chamado
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> capturarRegraDeNegocio(RuntimeException ex) {

        // 1. Pega a mensagem exata que escreveste no ContaService ("Saldo insuficiente!")
        String mensagem = ex.getMessage();

        // 2. Monta o nosso JSON elegante com Status 400 (Bad Request)
        ErroResposta erroFormatado = new ErroResposta(mensagem, HttpStatus.BAD_REQUEST.value());

        // 3. Devolve a resposta limpa para o cliente
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroFormatado);
    }
}