package com.minibanco.minibanco.dto;

import java.math.BigDecimal;

public class TransferenciaDTO {
    private Long idContaOrigem;
    private Long idContaDestino;
    private BigDecimal valor;

    // --- Getters e Setters ---
    public Long getIdContaOrigem() { return idContaOrigem; }
    public void setIdContaOrigem(Long idContaOrigem) { this.idContaOrigem = idContaOrigem; }
    public Long getIdContaDestino() { return idContaDestino; }
    public void setIdContaDestino(Long idContaDestino) { this.idContaDestino = idContaDestino; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}