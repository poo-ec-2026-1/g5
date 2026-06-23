package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import model.AnuncioTroca;
import model.AnuncioRepository;
import java.util.Optional;

public class TradeManager {
    public static void proporTroca(AnuncioTroca anuncio) {
        if (anuncio == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Proposta de Troca");
        dialog.setHeaderText("Você está propondo uma troca para o livro '" + anuncio.getLivro().getTitulo() + "'\n"
                           + "O anunciante procura por: '" + anuncio.getProcura() + "'");
        dialog.setContentText("Digite o título do livro que você oferece em troca:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String livroOferecido = result.get().trim();
            
            // Remove do banco de dados (troca concluída)
            AnuncioRepository repo = new AnuncioRepository();
            repo.removerAnuncio(anuncio);
            
            // Mostra informações do vendedor
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Troca Solicitada");
            alert.setHeaderText("Proposta de Troca Enviada com Sucesso!");
            alert.setContentText("Você ofereceu o livro: '" + livroOferecido + "'\n"
                               + "Em troca de: '" + anuncio.getLivro().getTitulo() + "'\n\n"
                               + "Entre em contato com o anunciante para combinar a entrega:\n"
                               + "Nome: " + anuncio.getVendedor().getNome() + "\n"
                               + "Telefone: " + anuncio.getVendedor().getFone() + "\n"
                               + "E-mail: " + anuncio.getVendedor().getEmail());
            alert.showAndWait();
            
            // Recarrega o catálogo
            if (ViewController.getInstance() != null) {
                ViewController.getInstance().navToCatalog();
            }
        } else if (result.isPresent()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Campo Vazio");
            alert.setContentText("Você precisa digitar o título de um livro para propor a troca.");
            alert.showAndWait();
        }
    }
}
