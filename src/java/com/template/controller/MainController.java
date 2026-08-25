package com.template.controller;

import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
import com.template.validator.BandaValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.template.util.DialogUtil.*;

public class MainController {

    @FXML private TextField txtNome;
    @FXML private TextField txtGenero;
    @FXML private TextField txtDataFormacao;
    @FXML private TextField txtCidadeOrigem;

    @FXML private Button btnCadastrar;
    @FXML private Button btnEditar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    @FXML private TableView<BandaDTO> tblBandas;
    @FXML private TableColumn<BandaDTO, Integer> colId;
    @FXML private TableColumn<BandaDTO, String> colNome;
    @FXML private TableColumn<BandaDTO, String> colGenero;
    @FXML private TableColumn<BandaDTO, String> colDataFormacao;
    @FXML private TableColumn<BandaDTO, String> colCidadeOrigem;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        colDataFormacao.setCellFactory(column -> new TableCell<BandaDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    BandaDTO banda = (BandaDTO) getTableRow().getItem();
                    if (banda.getDataFormacao() != null) {

                        DateTimeFormatter dataBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        setText(banda.getDataFormacao().format(dataBr));
                    } else {
                        setText("");
                    }
                }
            }
        });

        colCidadeOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));

        carregarBandas();
    }

    private void carregarBandas() {
        BandaDAO bandaDAO = new BandaDAO(); // DAO instanciado localmente
        ArrayList<BandaDTO> listaBandas = bandaDAO.listarBandas();
        tblBandas.setItems(FXCollections.observableArrayList(listaBandas));
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        String nome = txtNome.getText();
        String genero = txtGenero.getText();
        String data = txtDataFormacao.getText();
        String cidadeOrigem = txtCidadeOrigem.getText();

        if (!BandaValidator.validarBandas(nome, genero, data, cidadeOrigem)) {
            return;
        }

        DateTimeFormatter dataBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataFormacao = LocalDate.parse(data.trim(), dataBr);

        BandaDTO objDTO = new BandaDTO(nome, genero, dataFormacao, cidadeOrigem);

        BandaDAO bandaDAO = new BandaDAO();
        bandaDAO.cadastrarBanda(objDTO);

        showInformation("Banda cadastrada com sucesso!");

        carregarBandas();
        limparCampos();
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        // checa se tem uma banda selecionada
        if (bandaSelecionada != null) {
            String nome = txtNome.getText();
            String genero = txtGenero.getText();
            String data = txtDataFormacao.getText();
            String cidadeOrigem = txtCidadeOrigem.getText();

            //valida a banda
            if (!BandaValidator.validarBandas(nome, genero, data, cidadeOrigem)) {
                return;
            }

            DateTimeFormatter dataBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormacao = LocalDate.parse(data.trim(), dataBr);

            BandaDTO objDTO = new BandaDTO(bandaSelecionada.getId(), nome, genero, dataFormacao, cidadeOrigem);

            BandaDAO bandaDAO = new BandaDAO();
            bandaDAO.atualizarBanda(objDTO);

            showInformation("Banda atualizada com sucesso!");

            carregarBandas();
            limparCampos();
        } else {
            showWarning("Por favor, selecione uma banda na tabela primeiro!");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            boolean confirmou = showConfirmation("Deseja remover essa banda?");

            if (confirmou) {
                BandaDAO bandaDAO = new BandaDAO();
                bandaDAO.removerBanda(bandaSelecionada.getId());
                showInformation("Banda removida com sucesso!");
                carregarBandas();
                limparCampos();
            }
        } else {
            showWarning("Por favor, selecione uma banda na tabela primeiro!");
        }
    }

    @FXML
    private void carregarCampos() {
        BandaDTO objDTO = tblBandas.getSelectionModel().getSelectedItem();

        if (objDTO != null) {
            txtNome.setText(objDTO.getNome());
            txtGenero.setText(objDTO.getGenero());

            if (objDTO.getDataFormacao() != null) {
                DateTimeFormatter dataBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtDataFormacao.setText(objDTO.getDataFormacao().format(dataBr));
            }

            txtCidadeOrigem.setText(objDTO.getCidadeOrigem());
        }
    }

    private void limparCampos() {
        tblBandas.getSelectionModel().clearSelection();
        txtNome.clear();
        txtGenero.clear();
        txtDataFormacao.clear();
        txtCidadeOrigem.clear();
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }
}