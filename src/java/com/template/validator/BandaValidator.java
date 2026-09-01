package com.template.validator;

import java.util.ArrayList;
import java.util.List;
import static com.template.util.DialogUtil.showWarning;


public class BandaValidator implements IBandaValidator{

    public static boolean validarBandas(String nome, String genero, String data, String cidadeOrigem) {
        List<Validator<String>> validadores = new ArrayList<>();

        validadores.add(new CampoObrigatorioValidator("Nome", nome));
        validadores.add(new CampoObrigatorioValidator("Gênero", genero));
        validadores.add(new CampoObrigatorioValidator("Data de Formação", data));
        validadores.add(new CampoObrigatorioValidator("Cidade de Origem", cidadeOrigem));
        //recebe os campos
        validadores.add(new DataValidator(data));//valida o campo data

        for (Validator<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {//valida se os campos estão vazios
                showWarning(validador.getMensagemErro());
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean validarBanda(String nome, String genero, String dataFormacao, String cidadeOrigem) {
        return null;
    }

    @Override
    public Boolean ValidarNome(String nome) {
        return null;
    }

    @Override
    public Boolean ValidarGenero(String genero) {
        return null;
    }

    @Override
    public Boolean ValidarDataFormacao(String dataFormacao) {
        return null;
    }

    @Override
    public Boolean ValidarCidadeOrigem(String cidadeOrigem) {
        return null;
    }
}
