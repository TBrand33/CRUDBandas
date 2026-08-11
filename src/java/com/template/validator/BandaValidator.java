package com.template.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.template.util.DialogUtil.showWarning;

public class BandaValidator
{
    public static boolean validarBandas(String nome, String genero, String data, String cidadeOrigem) {
        if (nome == null || nome.trim().isEmpty() ||
                genero == null || genero.trim().isEmpty() ||
                data == null || data.trim().isEmpty() || cidadeOrigem == null || cidadeOrigem.trim().isEmpty()) {
            showWarning("Preencha todos os campos antes de prosseguir");
            return false;
        }
        if(!validarFormatoData(data)){
            showWarning("Formato de data inválido! Digite como dd/MM/yyyy (Ex: 23/07/2021).");
            return false;
        }
            return true;
    }

    public static boolean validarFormatoData(String data){
        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(data.trim(), formatoBr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
