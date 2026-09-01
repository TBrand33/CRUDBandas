package com.template.service;

import com.template.model.dto.BandaDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IBandaService {
    void cadastrar(String nome, String genero, String data, String cidadeOrigem);
    void editar(int id, String nome, String genero, String data, String cidadeOrigem);
    void excluir(int id);
    ArrayList<BandaDTO>listarTodas();
}
