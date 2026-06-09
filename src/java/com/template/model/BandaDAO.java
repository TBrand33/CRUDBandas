package com.template.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BandaDAO { // Se comunica com o banco
    private static final Logger logger = Logger.getLogger(BandaDAO.class.getName());
    private ArrayList<BandaDTO> listaBandas = new ArrayList<>();//Cria uma lista de bandas

    public ArrayList<BandaDTO> listarBandas() {
        listaBandas.clear(); // Limpa a lista antes de carregar
        String sql = "select * from bandas";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String genero = rs.getString("genero");

                LocalDate dataFormacao = null;
                java.sql.Date dbDate = rs.getDate("data_formacao");
                if (dbDate != null) {
                    dataFormacao = dbDate.toLocalDate();
                }

                String cidadeOrigem = rs.getString("cidade_origem");

                // Cria o objeto DTO com ID
                BandaDTO banda = new BandaDTO(id, nome, genero, dataFormacao, cidadeOrigem);
                listaBandas.add(banda);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao listar bandas", ex);
        }
        return listaBandas;
    }

    public void cadastrarBanda(BandaDTO banda) {
        String sql = "insert into bandas (nome, genero, data_formacao, cidade_origem) values (?,?,?,?)";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, banda.getNome());
            ps.setString(2, banda.getGenero());
            ps.setObject(3, banda.getDataFormacao());
            ps.setString(4, banda.getCidadeOrigem());
            ps.execute();
            System.out.println("Banda cadastrada com sucesso!");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar banda", ex);
        }
    }

    public void atualizarBanda(BandaDTO banda) {
        String sql = "update bandas set nome = ?, genero = ?, data_formacao = ?, cidade_origem = ? where id = ?";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, banda.getNome());
            ps.setString(2, banda.getGenero());
            ps.setObject(3, banda.getDataFormacao());
            ps.setString(4, banda.getCidadeOrigem());
            ps.setInt(5, banda.getId());
            ps.execute();
            System.out.println("Banda Atualizada com sucesso!");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao atualizar banda", ex);
        }
    }

    public void removerBanda(int id) {
        String sql = "delete from bandas where id = ?";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            System.out.println("Banda Removida com sucesso");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao remover Banda", ex);
        }
    }
}
