package com.template.validator;

public class CampoObrigatorioValidator implements Validator<String> {

    private final String nomeCampo;
    private final String valor;

    public CampoObrigatorioValidator(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        return valorAtual != null && !valorAtual.trim().isEmpty();//retorna false se estiver vazio
    }

    @Override
    public String getMensagemErro() {
        return "Preencha o campo '" + nomeCampo + "' antes de prosseguir.";
    }

    @Override
    public String getValor() {
        return valor;
    }

}
