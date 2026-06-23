package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Anuncio;
import model.AnuncioVenda;
import model.AnuncioTroca;

public class BookDetailsController {
    private final Anuncio anuncio;

    @FXML private Label lblTitulo;
    @FXML private Label lblAutor;
    @FXML private Label lblEstado;
    @FXML private Label lblIsbn;
    @FXML private Label lblValorOuTroca;
    @FXML private Label lblDescricao;
    @FXML private Label lblVendedorNome;
    @FXML private Label lblVendedorContato;
    @FXML private Button btnAcao;

    public BookDetailsController(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    @FXML
    public void initialize() {
        if (anuncio != null) {
            if (anuncio.getLivro() != null) {
                lblTitulo.setText(anuncio.getLivro().getTitulo());
                lblAutor.setText("Autor: " + anuncio.getLivro().getAutor());
                lblEstado.setText("Estado: " + anuncio.getLivro().getEstado());
                lblIsbn.setText("ISBN: " + (anuncio.getLivro().getIsbn() != null && !anuncio.getLivro().getIsbn().isEmpty() ? anuncio.getLivro().getIsbn() : "N/A"));
            } else {
                lblTitulo.setText("Título Desconhecido");
                lblAutor.setText("Autor: N/A");
                lblEstado.setText("Estado: N/A");
                lblIsbn.setText("ISBN: N/A");
            }
            
            lblDescricao.setText(anuncio.getDescricao() != null && !anuncio.getDescricao().trim().isEmpty() ? anuncio.getDescricao() : "Nenhuma descrição fornecida.");

            if (anuncio instanceof AnuncioVenda) {
                lblValorOuTroca.setText(String.format("R$ %.2f", anuncio.getPreco()));
            } else if (anuncio instanceof AnuncioTroca) {
                AnuncioTroca troca = (AnuncioTroca) anuncio;
                lblValorOuTroca.setText("Troca por: " + troca.getProcura());
                btnAcao.setText("🔄 Solicitar Troca");
            }

            if (anuncio.getVendedor() != null) {
                lblVendedorNome.setText(anuncio.getVendedor().getNome());
                lblVendedorContato.setText("Telefone: " + anuncio.getVendedor().getFone() + " | E-mail: " + anuncio.getVendedor().getEmail());
            } else {
                lblVendedorNome.setText("Vendedor Desconhecido");
                lblVendedorContato.setText("Contato não disponível");
            }
        }
    }

    @FXML
    private void handleVoltar() {
        if (ViewController.getInstance() != null) {
            ViewController.getInstance().navToCatalog();
        }
    }

    @FXML
    private void handleAdicionarAoCarrinho() {
        if (anuncio instanceof AnuncioTroca) {
            TradeManager.proporTroca((AnuncioTroca) anuncio);
        } else {
            CartManager.getInstance().addItem(anuncio);
        }
    }
}
