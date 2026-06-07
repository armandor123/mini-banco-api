package com.minibanco.minibanco.dto;

import java.math.BigDecimal;

public class DepositoDTO {
    private BigDecimal valor;
    private String origemTransacao; // ex: "pix_banco_A", "Doc", "ted"
    private String tokenIdempotencia; //COdigo unico gerado pelo app para evitar duplicidade

    //GETTERS E SETTERS

    public  BigDecimal getValor() {return valor;}
    public void  setValor(BigDecimal valor) {this.valor = valor;}
    public  String getOrigemTransacao() {return origemTransacao;}
    public void  setOrigemTransacao(String origemTransacao) { this.origemTransacao = origemTransacao;};
    public String getTokenIdempotencia() {return tokenIdempotencia;}

    public void setTokenIdempotencia(String tokenIdempotencia) {
        this.tokenIdempotencia = tokenIdempotencia;
    }
}
