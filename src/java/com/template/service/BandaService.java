package com.template.service;

import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
import com.template.validator.BandaValidator;

import java.util.ArrayList;

public class BandaService implements  IBandaService{

    private final BandaDAO bandaDAO = new BandaDAO();

    public void cadastrar(String nome, String genero, String data, String cidadeOrigem) {
        if (!BandaValidator.validarBandas(nome, genero, data, cidadeOrigem)) {
            return ;
        }

        BandaDTO dto = new BandaDTO(
                nome,
                genero,
                DataService.parseDaString(data),
                cidadeOrigem
        );

        bandaDAO.cadastrarBanda(dto);
        return ;
    }

    public void editar(int id, String nome, String genero, String data, String cidadeOrigem) {
        if (!BandaValidator.validarBandas(nome, genero, data, cidadeOrigem)) {
            return ;
        }

        BandaDTO dto = new BandaDTO(
                id,
                nome,
                genero,
                DataService.parseDaString(data),
                cidadeOrigem
        );

        bandaDAO.atualizarBanda(dto);
        return;
    }

    public void excluir(int id) {
        bandaDAO.removerBanda(id);
    }

    public ArrayList<BandaDTO> listarTodas() {
        return bandaDAO.listarBandas();
    }
}