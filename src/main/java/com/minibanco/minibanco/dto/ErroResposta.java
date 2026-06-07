package com.minibanco.minibanco.dto;

import java.time.LocalDateTime;

public class ErroResposta {
    private String mensagem;
    private Integer status;
    private LocalDateTime dataHora;

    public ErroResposta(String mensagem, Integer status) {
        this.mensagem = mensagem;
        this.status = status;
        this.dataHora = LocalDateTime.now(); // Pega a data e hora exata do erro
    }

    // Getters para o Spring conseguir transformar isto em JSON
    public String getMensagem() { return mensagem; }
    public Integer getStatus() { return status; }
    public LocalDateTime getDataHora() { return dataHora; }
}
