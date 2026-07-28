package com.template.model.dto;

import java.time.LocalDate;

public class BandaDTO { //Reune e transporta os dados para o DAO
    private int id;
    private String nome;
    private String genero;
    private LocalDate dataFormacao;
    private String cidadeOrigem;

    //Construtor sem ID para cadastro
    public BandaDTO( String nome, String genero, LocalDate dataFormacao, String cidadeOrigem) {
        this.nome = nome;
        this.genero = genero;
        this.dataFormacao = dataFormacao;
        this.cidadeOrigem = cidadeOrigem;
    }
    //Construtor com id para atualizar banda
    public BandaDTO(int id, String nome, String genero, LocalDate dataFormacao, String cidadeOrigem) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.dataFormacao = dataFormacao;
        this.cidadeOrigem = cidadeOrigem;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getDataFormacao() {
        return dataFormacao;
    }

    public void setDataFormacao(LocalDate dataFormacao) {
        this.dataFormacao = dataFormacao;
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

}
