package com.template.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DataValidator implements Validator<String> {

    private final String data;

    public DataValidator(String data) {
        this.data = data;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (valorAtual == null || valorAtual.trim().isEmpty()) {
            return true;
        }

        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(valorAtual.trim(), formatoBr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "Formato de data inválido! Digite como dd/MM/yyyy (Ex: 23/07/2021).";
    }

    @Override
    public String getValor() {
        return data;
    }
}