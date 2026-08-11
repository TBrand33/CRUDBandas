package com.template.controller;

import com.template.validator.BandaValidator;
import java.time.format.DateTimeFormatter;
import com.template.model.dao.BandaDAO;
import com.template.model.dto.BandaDTO;
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
        colDataFormacao.setCellFactory(column -> new javafx.scene.control.TableCell<BandaDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    BandaDTO banda = (BandaDTO) getTableRow().getItem();
                    if (banda.getDataFormacao() != null) {
                        setText(banda.getDataFormacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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
        BandaDAO objBandaDAO = new BandaDAO();
        ArrayList<BandaDTO> listaBandas = objBandaDAO.listarBandas();
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

        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataFormacao = LocalDate.parse(data.trim(), formatoBr);

        BandaDTO objDTO = new BandaDTO(nome, genero, dataFormacao, cidadeOrigem);
        BandaDAO objDAO = new BandaDAO();
        objDAO.cadastrarBanda(objDTO);

        showInformation("Banda cadastrada com sucesso!");

        carregarBandas();
        limparCampos();
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            String nome = txtNome.getText();
            String genero = txtGenero.getText();
            String data = txtDataFormacao.getText();
            String cidadeOrigem = txtCidadeOrigem.getText();

            // Validação usando o BandaValidator
            if (!BandaValidator.validarBandas(nome, genero, data, cidadeOrigem)) {
                return;
            }

            DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormacao = LocalDate.parse(data.trim(), formatoBr);

            BandaDTO objDTO = new BandaDTO(bandaSelecionada.getId(), nome, genero, dataFormacao, cidadeOrigem);
            BandaDAO objDAO = new BandaDAO();
            objDAO.atualizarBanda(objDTO);

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
            BandaDAO objDAO = new BandaDAO();
            objDAO.removerBanda(bandaSelecionada.getId());

            showConfirmation("Deseja remover essa banda?");
            showInformation("Banda removida com sucesso!");

            carregarBandas();
            limparCampos();
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

            DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtDataFormacao.setText(objDTO.getDataFormacao().format(formatoBr));

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