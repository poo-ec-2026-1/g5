package controller;

import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.AnuncioTroca;
import model.AnuncioRepository;
import model.Usuario;
import model.Proposta;
import model.PropostaRepository;
import java.util.Optional;

public class TradeManager {
    public static void proporTroca(AnuncioTroca anuncio) {
        if (anuncio == null) return;

        // Impede trocar livro postado por si mesmo
        Usuario logado = SessionManager.getInstance().getUsuarioLogado();
        if (logado != null && anuncio.getVendedor() != null && 
            logado.getEmail().equals(anuncio.getVendedor().getEmail())) {
            AlertHelper.showWarning(
                "Aviso",
                "Ação Inválida",
                "Você não pode propor uma troca para o seu próprio anúncio."
            );
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Proposta de Troca");
        dialog.setHeaderText("Você está propondo uma troca para o livro '" + anuncio.getLivro().getTitulo() + "'\n"
                           + "O anunciante procura por: '" + anuncio.getProcura() + "'");
        dialog.setContentText("Digite o título do livro que você oferece em troca:");

        // Estilização do TextInputDialog com ícone e CSS
        try {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(TradeManager.class.getResourceAsStream("/resources/images/librarylogo1.png")));
            
            ImageView logoView = new ImageView(new Image(TradeManager.class.getResourceAsStream("/resources/images/librarylogo1.png")));
            logoView.setFitWidth(40);
            logoView.setFitHeight(40);
            logoView.setPreserveRatio(true);
            
            // Container com fundo escuro para destacar o logo branco
            StackPane logoContainer = new StackPane(logoView);
            logoContainer.setStyle("-fx-background-color: #1E1E1E; -fx-background-radius: 8; -fx-padding: 8;");
            dialog.setGraphic(logoContainer);
            
            dialog.getDialogPane().getStylesheets().add(TradeManager.class.getResource("/view/application.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("custom-alert");
        } catch (Exception e) {
            System.err.println("Não foi possível estilizar o diálogo de entrada: " + e.getMessage());
        }

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String livroOferecido = result.get().trim();
            
            if (logado == null) {
                AlertHelper.showWarning("Aviso", "Erro de Sessão", "Você precisa estar logado para propor uma troca.");
                return;
            }
            
            Proposta proposta = new Proposta(anuncio, logado, livroOferecido);
            PropostaRepository propostaRepo = new PropostaRepository();
            boolean salva = propostaRepo.salvar(proposta);
            
            if (salva) {
                AlertHelper.showInfo(
                    "Proposta Enviada",
                    "Proposta de Troca Enviada com Sucesso!",
                    "Sua proposta oferecendo o livro '" + livroOferecido + "' em troca de '" + anuncio.getLivro().getTitulo() + "' foi enviada para o anunciante.\n\n"
                    + "Aguarde a avaliação dele. Você receberá o contato quando ela for aceita."
                );
            } else {
                AlertHelper.showWarning("Erro", "Erro ao salvar", "Ocorreu um erro ao enviar sua proposta. Tente novamente.");
            }
            
            // Recarrega o catálogo
            if (ViewController.getInstance() != null) {
                ViewController.getInstance().navToCatalog();
            }
        } else if (result.isPresent()) {
            AlertHelper.showWarning(
                "Aviso",
                "Campo Vazio",
                "Você precisa digitar o título de um livro para propor a troca."
            );
        }
    }
}
