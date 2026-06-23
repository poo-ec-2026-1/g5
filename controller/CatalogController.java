package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import model.Anuncio;
import model.AnuncioRepository;

import java.io.IOException;
import java.util.List;

public class CatalogController {

    @FXML
    private FlowPane cardsContainer;

    @FXML
    public void initialize() {
        AnuncioRepository repo = new AnuncioRepository();
        List<Anuncio> anuncios = repo.listarTodos();
        
        if (anuncios.isEmpty()) {
            javafx.scene.control.Label label = new javafx.scene.control.Label("Nenhum livro no catálogo");
            label.setStyle("-fx-font-size: 18px; -fx-text-fill: #6C757D; -fx-font-style: italic;");
            cardsContainer.getChildren().add(label);
        } else {
            for (Anuncio anuncio : anuncios) {
                carregarCard(anuncio);
            }
        }
    }

    private void carregarCard(Anuncio anuncio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookCard.fxml"));
            BookCardController cardController = new BookCardController(anuncio);
            loader.setController(cardController);
            Parent card = loader.load();
            cardsContainer.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
