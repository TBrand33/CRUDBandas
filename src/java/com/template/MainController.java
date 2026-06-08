package com.template;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.template.model.BandaDAO;
import com.template.model.BandaDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainController {

    // VARIÁVEL OCULTA: Guarda o ID da banda selecionada na tabela para usar no Atualizar e Excluir
    private int idBandaSelecionada = -1;

    // Apenas os campos visíveis que o usuário preenche
    @FXML private TextField txtNome;
    @FXML private TextField txtGenero;
    @FXML private TextField txtDataFormacao; // Formato esperado: AAAA-MM-DD
    @FXML private TextField txtCidadeOrigem;

    @FXML private Button btnCadastrar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnExcluir;

    @FXML private TableView<BandaDTO> tblBandas;
    @FXML private TableColumn<BandaDTO, Integer> colId; // Mantemos na tabela para o usuário ver o ID gerado
    @FXML private TableColumn<BandaDTO, String> colNome;
    @FXML private TableColumn<BandaDTO, String> colGenero;
    @FXML private TableColumn<BandaDTO, String> colDataFormacao;
    @FXML private TableColumn<BandaDTO, String> colCidadeOrigem;

    @FXML
    public void initialize() {
        // Vincula as colunas da tabela aos atributos do BandaDTO
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colDataFormacao.setCellValueFactory(new PropertyValueFactory<>("dataFormacao"));
        colCidadeOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));

        carregarBandas();
    }

    private void carregarBandas() {
        BandaDAO objBandaDAO = new BandaDAO();
        ArrayList<BandaDTO> listaBandas = objBandaDAO.listarBandas();
        tblBandas.setItems(FXCollections.observableArrayList(listaBandas));
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        String nome = txtNome.getText();
        String genero = txtGenero.getText();
        String cidadeOrigem = txtCidadeOrigem.getText();

        // Validação básica se a data está vazia
        if (txtDataFormacao.getText() == null || txtDataFormacao.getText().trim().isEmpty()) {
            System.out.println("Por favor, insira a data de formação!");
            return;
        }

        try {
            // Define o formato brasileiro dd/MM/yyyy
            DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormacao = LocalDate.parse(txtDataFormacao.getText(), formatoBr);

            // Usa o construtor SEM ID (o banco cuidará do autoincremento)
            BandaDTO objbandadto = new BandaDTO(nome, genero, dataFormacao, cidadeOrigem);

            BandaDAO objbandadao = new BandaDAO();
            objbandadao.cadastrarBanda(objbandadto);

            carregarBandas(); // Atualiza a tabela
            limparCampos();
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido! Digite como dd/MM/yyyy (Ex: 23/07/2021).");
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        // Só permite atualizar se o usuário clicou em alguma banda da tabela antes (id diferente de -1)
        if (idBandaSelecionada != -1) {
            String nome = txtNome.getText();
            String genero = txtGenero.getText();
            String cidadeOrigem = txtCidadeOrigem.getText();

            try {
                // Define o formato brasileiro dd/MM/yyyy
                DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataFormacao = LocalDate.parse(txtDataFormacao.getText(), formatoBr);

                // Usa o construtor COM ID, passando a nossa variável de controle interna
                BandaDTO objbandadto = new BandaDTO(idBandaSelecionada, nome, genero, dataFormacao, cidadeOrigem);

                BandaDAO objbandadao = new BandaDAO();
                objbandadao.atualizarBanda(objbandadto);

                carregarBandas();
                limparCampos();
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Formato de data inválido! Digite como dd/MM/yyyy (Ex: 23/07/2021).");
            }
        } else {
            System.out.println("Por favor, selecione uma banda na tabela primeiro!");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        // Só permite excluir se houver uma banda selecionada
        if (idBandaSelecionada != -1) {
            BandaDAO objbandadao = new BandaDAO();
            objbandadao.removerBanda(idBandaSelecionada);

            carregarBandas();
            limparCampos();
        } else {
            System.out.println("Por favor, selecione uma banda na tabela primeiro!");
        }
    }

    // Método acionado ao clicar em uma linha da tabela (On Mouse Clicked no Scene Builder)
    @FXML
    private void carregarCampos() {
        BandaDTO objBandaDTO = tblBandas.getSelectionModel().getSelectedItem();

        if (objBandaDTO != null) {
            // Captura o ID do banco que veio na tabela e guarda na nossa variável oculta
            idBandaSelecionada = objBandaDTO.getId();

            // Preenche apenas os campos de texto comuns
            txtNome.setText(objBandaDTO.getNome());
            txtGenero.setText(objBandaDTO.getGenero());
            txtDataFormacao.setText(objBandaDTO.getDataFormacao().toString());
            txtCidadeOrigem.setText(objBandaDTO.getCidadeOrigem());
        }
    }

    private void limparCampos() {
        idBandaSelecionada = -1; // Reseta a seleção para segurança
        txtNome.clear();
        txtGenero.clear();
        txtDataFormacao.clear();
        txtCidadeOrigem.clear();
    }
}