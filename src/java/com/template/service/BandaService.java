package com.template.service;

import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
import com.template.validator.BandaValidator;
import java.util.List;

public class BandaService {

    private final BandaDAO bandaDAO = new BandaDAO();

    public boolean cadastrar(String nome, String genero, String data, String cidadeOrigem) {
        if (!BandaValidator.validarBandas(nome, genero, data, cidadeOrigem)) {
            return false;
        }

        BandaDTO dto = new BandaDTO(
                nome,
                genero,
                DataService.parseDaString(data),
                cidadeOrigem
        );

        bandaDAO.cadastrarBanda(dto);
        return true;
    }

    public boolean editar(int id, String nome, String genero, String data, String cidadeOrigem) {
        if (!BandaValidator.validarBandas(nome, genero, data, cidadeOrigem)) {
            return false;
        }

        BandaDTO dto = new BandaDTO(
                id,
                nome,
                genero,
                DataService.parseDaString(data),
                cidadeOrigem
        );

        bandaDAO.atualizarBanda(dto);
        return true;
    }

    public void excluir(int id) {
        bandaDAO.removerBanda(id);
    }

    public List<BandaDTO> listarTodas() {
        return bandaDAO.listarBandas();
    }
}