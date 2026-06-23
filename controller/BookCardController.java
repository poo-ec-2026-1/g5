package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Anuncio;
import model.AnuncioVenda;
import model.AnuncioTroca;

public class BookCardController {
    private final Anuncio anuncio;

    @FXML private Label lblNomeLivro;
    @FXML private Label lblAutorLivro;
    @FXML private Label lblNomeVendedor;
    @FXML private Label lblPrecoOuTroca;

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
        CartManager.getInstance().addItem(anuncio);
    }
}
