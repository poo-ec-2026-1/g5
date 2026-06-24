package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Anuncio;
import model.AnuncioTroca;
import model.AnuncioVenda;

import java.util.List;

public class CartController {

    @FXML
    private VBox cartItemsContainer;

    @FXML
    private Label lblTotalItens;

    @FXML
    private Label lblPrecoTotal;

    @FXML
    private Button btnCheckout;

    @FXML
    private Button btnClear;

    @FXML
    public void initialize() {
        recarregarItens();
    }

    private void recarregarItens() {
        cartItemsContainer.getChildren().clear();
        List<Anuncio> items = CartManager.getInstance().getItems();

        if (items.isEmpty()) {
            Label lblVazio = new Label("Seu carrinho está vazio.");
            lblVazio.setStyle("-fx-font-size: 16px; -fx-text-fill: #6C757D; -fx-font-style: italic;");
            cartItemsContainer.getChildren().add(lblVazio);
            
            btnCheckout.setDisable(true);
            btnClear.setDisable(true);
        } else {
            btnCheckout.setDisable(false);
            btnClear.setDisable(false);
            
            for (Anuncio item : items) {
                HBox card = criarItemCard(item);
                cartItemsContainer.getChildren().add(card);
            }
        }

        lblTotalItens.setText(String.valueOf(items.size()));
        lblPrecoTotal.setText(String.format("R$ %.2f", CartManager.getInstance().getTotal()));
    }

    private HBox criarItemCard(Anuncio item) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(15.0);
        hBox.setPadding(new Insets(15.0));
        hBox.getStyleClass().add("surface");
        hBox.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

        // Miniatura do Livro
        ImageView imageView = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/resources/images/livroGenerico.png"));
            imageView.setImage(img);
        } catch (Exception e) {
            // Em caso de fallback se não encontrar a imagem
        }
        imageView.setFitHeight(60.0);
        imageView.setFitWidth(40.0);
        imageView.setPreserveRatio(true);

        // Detalhes do Livro (Título, Autor, Vendedor)
        VBox vBox = new VBox();
        vBox.setSpacing(5.0);
        
        String titulo = item.getLivro() != null ? item.getLivro().getTitulo() : "Título Desconhecido";
        String autor = item.getLivro() != null ? item.getLivro().getAutor() : "Autor Desconhecido";
        String vendedor = item.getVendedor() != null ? item.getVendedor().getNome() : "Desconhecido";

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #212529;");
        lblTitulo.setWrapText(true);

        Label lblAutor = new Label("Autor: " + autor);
        lblAutor.setStyle("-fx-font-size: 13px; -fx-text-fill: #6C757D;");
        lblAutor.setWrapText(true);

        Label lblVendedor = new Label("Vendedor: " + vendedor);
        lblVendedor.setStyle("-fx-font-size: 13px; -fx-text-fill: #6C757D;");
        lblVendedor.setWrapText(true);

        vBox.getChildren().addAll(lblTitulo, lblAutor, lblVendedor);
        HBox.setHgrow(vBox, Priority.ALWAYS);

        // Informação de Preço ou Troca
        Label lblPreco = new Label();
        lblPreco.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2D6A4F;");
        
        if (item instanceof AnuncioVenda) {
            lblPreco.setText(String.format("R$ %.2f", item.getPreco()));
        } else if (item instanceof AnuncioTroca) {
            AnuncioTroca troca = (AnuncioTroca) item;
            lblPreco.setText("Troca por: " + (troca.getProcura().length() > 20 ? troca.getProcura().substring(0, 18) + "..." : troca.getProcura()));
        }

        // Botão Remover
        Button btnRemover = new Button("Remover");
        btnRemover.setStyle("-fx-background-color: #DC3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 5 10 5 10; -fx-cursor: hand;");
        btnRemover.setOnAction(e -> {
            CartManager.getInstance().removeItem(item);
            recarregarItens();
        });

        hBox.getChildren().addAll(imageView, vBox, lblPreco, btnRemover);
        return hBox;
    }

    @FXML
    private void handleCheckout() {
        CartManager.getInstance().checkout();
        recarregarItens();
    }

    @FXML
    private void handleClear() {
        CartManager.getInstance().clear();
        recarregarItens();
    }
}
