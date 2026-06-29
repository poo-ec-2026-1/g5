package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Anuncio;
import model.AnuncioVenda;
import model.AnuncioTroca;
import model.Usuario;

public class BookCardController {
    private final Anuncio anuncio;

    @FXML private Label lblNomeLivro;
    @FXML private Label lblAutorLivro;
    @FXML private Label lblNomeVendedor;
    @FXML private Label lblPrecoOuTroca;
    @FXML private Button btnAcao;

    public BookCardController(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    @FXML
    public void initialize() {
        if (anuncio != null) {
            if (anuncio.getLivro() != null) {
                lblNomeLivro.setText(anuncio.getLivro().getTitulo());
                lblAutorLivro.setText(anuncio.getLivro().getAutor());
            } else {
                lblNomeLivro.setText("Título Desconhecido");
                lblAutorLivro.setText("Autor Desconhecido");
            }

            if (anuncio.getVendedor() != null) {
                lblNomeVendedor.setText("Vendedor: " + anuncio.getVendedor().getNome());
            } else {
                lblNomeVendedor.setText("Vendedor: Desconhecido");
            }

            if (anuncio instanceof AnuncioVenda) {
                lblPrecoOuTroca.setText(String.format("R$ %.2f", anuncio.getPreco()));
            } else if (anuncio instanceof AnuncioTroca) {
                AnuncioTroca troca = (AnuncioTroca) anuncio;
                lblPrecoOuTroca.setText("Troca por: " + troca.getProcura());
                btnAcao.setText("Trocar");
            }

            // Impede comprar/trocar livro postado por si mesmo
            Usuario logado = SessionManager.getInstance().getUsuarioLogado();
            if (logado != null && anuncio.getVendedor() != null && 
                logado.getEmail().equals(anuncio.getVendedor().getEmail())) {
                btnAcao.setText("Seu Anúncio");
                btnAcao.setDisable(true);
                btnAcao.setStyle("-fx-background-color: #6C757D; -fx-text-fill: white; -fx-opacity: 0.8;");
            }
        }
    }

    @FXML
    private void handleVerMais() {
        if (ViewController.getInstance() != null) {
            ViewController.getInstance().mostrarDetalhesLivro(anuncio);
        }
    }

    @FXML
    private void handleAdicionar() {
        if (anuncio instanceof AnuncioTroca) {
            TradeManager.proporTroca((AnuncioTroca) anuncio);
        } else {
            CartManager.getInstance().addItem(anuncio);
        }
    }
}
