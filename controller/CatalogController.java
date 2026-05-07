package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class CatalogController {

    @FXML
    private FlowPane cardsContainer;

    @FXML
    public void initialize() {
        // Como não estamos usando mocks com dados dinâmicos reais agora,
        // vamos apenas carregar alguns cards estáticos para demonstrar o visual
        // responsivo do FlowPane
        for (int i = 0; i < 6; i++) {
            carregarCard();
        }
    }

    private void carregarCard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookCard.fxml"));
            Parent card = loader.load();
            cardsContainer.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
