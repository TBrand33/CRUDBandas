package com.template;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.template.model.BandaDAO;
import com.template.model.BandaDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.ArrayList;

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

        txtNome.textProperty().addListener((observable, oldValue, newValue) -> validarCampos());
        txtGenero.textProperty().addListener((observable, oldValue, newValue) -> validarCampos());
        txtDataFormacao.textProperty().addListener((observable, oldValue, newValue) -> validarCampos());

        validarCampos();
        carregarBandas();
    }

    private void validarCampos() {
        String nome = txtNome.getText();
        String genero = txtGenero.getText();
        String data = txtDataFormacao.getText();

        if (nome == null || nome.trim().isEmpty() ||
                genero == null || genero.trim().isEmpty() ||
                data == null || data.trim().isEmpty()) {

            btnCadastrar.setDisable(true);
            btnEditar.setDisable(true);
            btnExcluir.setDisable(true);
            btnLimpar.setDisable(true);
        } else {
            btnExcluir.setDisable(false);
            btnLimpar.setDisable(false);
            btnCadastrar.setDisable(false);
            btnEditar.setDisable(false);
        }
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

        try {
            DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormacao = LocalDate.parse(txtDataFormacao.getText(), formatoBr);

            BandaDTO objDTO = new BandaDTO(nome, genero, dataFormacao, cidadeOrigem);
            BandaDAO objDAO = new BandaDAO();
            objDAO.cadastrarBanda(objDTO);

            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Banda cadastrada com sucesso!");

            carregarBandas();
            limparCampos();
        } catch (DateTimeParseException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro de Formato", "Formato de data inválido! Digite como dd/MM/yyyy (Ex: 23/07/2021).");
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            String nome = txtNome.getText();
            String genero = txtGenero.getText();
            String cidadeOrigem = txtCidadeOrigem.getText();

            try {
                DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataFormacao = LocalDate.parse(txtDataFormacao.getText(), formatoBr);

                BandaDTO objDTO = new BandaDTO(bandaSelecionada.getId(), nome, genero, dataFormacao, cidadeOrigem);
                BandaDAO objDAO = new BandaDAO();
                objDAO.atualizarBanda(objDTO);

                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Banda atualizada com sucesso!");

                carregarBandas();
                limparCampos();
            } catch (DateTimeParseException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro de Formato", "Formato de data inválido! Digite como dd/MM/yyyy (Ex: 23/07/2021).");
            }
        } else {
            exibirAlerta(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione uma banda na tabela primeiro!");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        BandaDTO bandaSelecionada = tblBandas.getSelectionModel().getSelectedItem();

        if (bandaSelecionada != null) {
            BandaDAO objDAO = new BandaDAO();
            objDAO.removerBanda(bandaSelecionada.getId()); // Passa o ID local

            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Banda removida com sucesso!");

            carregarBandas();
            limparCampos();
        } else {
            exibirAlerta(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione uma banda na tabela primeiro!");
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

            validarCampos();
        }
    }

    private void limparCampos() {
        tblBandas.getSelectionModel().clearSelection();

        txtNome.clear();
        txtGenero.clear();
        txtDataFormacao.clear();
        txtCidadeOrigem.clear();

        validarCampos();
    }

    @FXML
    private void btnLimparAction(ActionEvent event){
        limparCampos();
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}