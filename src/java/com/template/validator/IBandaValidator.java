package com.template.validator;

public interface IBandaValidator {
    Boolean validarBanda(String nome, String genero, String dataFormacao, String cidadeOrigem);
    Boolean ValidarNome(String nome);
    Boolean ValidarGenero(String genero);
    Boolean ValidarDataFormacao(String dataFormacao);
    Boolean ValidarCidadeOrigem(String cidadeOrigem);
}
