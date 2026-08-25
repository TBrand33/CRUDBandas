package com.template.controller;

import com.template.model.dto.BandaDTO;
import com.template.service.BandaService;
import com.template.service.DataService;
import com.template.util.ClearUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    // Instância do serviço que lidará com a regra de negócio
    private final BandaService bandaService = new BandaService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colCidadeOrigem.setCellValueFactory(new PropertyValueFactory<>("cidadeOrigem"));

        colDataFormacao.setCellFactory(column -> new TableCell<BandaDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    BandaDTO banda = (BandaDTO) getTableRow().getItem();
                    // Delega a formatação para o DataService
                    setText(DataService.formatarParaBR(banda.getDataFormacao()));
                }
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        tblBandas.setItems(FXCollections.observableArrayList(bandaService.listarTodas()));
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        boolean sucesso = bandaService.cadastrar(
                txtNome.getText(),
                txtGenero.getText(),
                txtDataFormacao.getText(),
                txtCidadeOrigem.getText()
        );

        if (sucesso) {
            showInformation("Banda cadastrada com sucesso!");
            atualizarTabela();
            btnLimparAction(null);
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            boolean sucesso = bandaService.editar(
                    bandaSelecionada.getId(),
                    txtNome.getText(),
                    txtGenero.getText(),
                    txtDataFormacao.getText(),
                    txtCidadeOrigem.getText()
            );

            if (sucesso) {
                showInformation("Banda atualizada com sucesso!");
                atualizarTabela();
                btnLimparAction(null);
            }
        } else {
            showWarning("Por favor, selecione uma banda na tabela primeiro!");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            if (showConfirmation("Deseja remover essa banda?")) {
                bandaService.excluir(bandaSelecionada.getId());
                showInformation("Banda removida com sucesso!");
                atualizarTabela();
                btnLimparAction(null);
            }
        } else {
            showWarning("Por favor, selecione uma banda na tabela primeiro!");
        }
    }

    @FXML
    private void carregarCampos() {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            txtNome.setText(bandaSelecionada.getNome());
            txtGenero.setText(bandaSelecionada.getGenero());
            txtCidadeOrigem.setText(bandaSelecionada.getCidadeOrigem());

            // Usando o DataService para jogar a data formatada na tela
            txtDataFormacao.setText(DataService.formatarParaBR(bandaSelecionada.getDataFormacao()));
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        // Delega a responsabilidade de limpar tudo para a classe utilitária
        ClearUtil.limparCampos(tblBandas, txtNome, txtGenero, txtDataFormacao, txtCidadeOrigem);
    }
}