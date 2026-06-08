package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String connectionUrl = "jdbc:postgresql://localhost:5432/bandas";
    private static final String usuario = "postgres";
    private static final String senha = "postgres";

    public Connection conectaBD() {
        try {
            // LINHA NOVA: Força o Java a carregar o driver do PostgreSQL na memória
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(connectionUrl, usuario, senha);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do PostgreSQL não foi encontrado no projeto!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}