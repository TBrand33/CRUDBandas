package com.template.util;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClearUtil {

    public static void limparCampos(TableView<?> tabela, TextField... campos) {
        if (tabela != null) {
            tabela.getSelectionModel().clearSelection();
        }

        for (TextField campo : campos) {
            if (campo != null) {
                campo.clear();
            }
        }
    }
}