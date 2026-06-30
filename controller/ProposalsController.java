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
import model.AnuncioRepository;
import model.Proposta;
import model.PropostaRepository;
import model.Transacao;
import model.TransacaoRepository;
import model.Usuario;
import java.util.List;

public class ProposalsController {

    @FXML
    private VBox proposalsContainer;

    private PropostaRepository propostaRepo;
    private TransacaoRepository transacaoRepo;
    private AnuncioRepository anuncioRepo;

    @FXML
    public void initialize() {
        propostaRepo = new PropostaRepository();
        transacaoRepo = new TransacaoRepository();
        anuncioRepo = new AnuncioRepository();
        
        recarregarPropostas();
    }

    private void recarregarPropostas() {
        proposalsContainer.getChildren().clear();
        Usuario logado = SessionManager.getInstance().getUsuarioLogado();
        
        if (logado == null) {
            Label lblErro = new Label("Você precisa estar logado para ver as propostas.");
            lblErro.setStyle("-fx-font-size: 16px; -fx-text-fill: #DC3545; -fx-font-style: italic;");
            proposalsContainer.getChildren().add(lblErro);
            return;
        }

        List<Proposta> propostas = propostaRepo.buscarRecebidasPorUsuario(logado);

        if (propostas.isEmpty()) {
            Label lblVazio = new Label("Nenhuma proposta de troca pendente.");
            lblVazio.setStyle("-fx-font-size: 16px; -fx-text-fill: #6C757D; -fx-font-style: italic;");
            proposalsContainer.getChildren().add(lblVazio);
        } else {
            for (Proposta proposta : propostas) {
                HBox card = criarPropostaCard(proposta);
                proposalsContainer.getChildren().add(card);
            }
        }
    }

    private HBox criarPropostaCard(Proposta proposta) {
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
            // Fallback
        }
        imageView.setFitHeight(60.0);
        imageView.setFitWidth(40.0);
        imageView.setPreserveRatio(true);

        // Detalhes da Proposta
        VBox vBox = new VBox();
        vBox.setSpacing(5.0);
        
        String tituloSolicitado = proposta.getAnuncio() != null && proposta.getAnuncio().getLivro() != null 
            ? proposta.getAnuncio().getLivro().getTitulo() 
            : "Livro Desconhecido";
        String proponenteNome = proposta.getProponente() != null ? proposta.getProponente().getNome() : "Desconhecido";
        String livroOferecido = proposta.getLivroOferecido() != null ? proposta.getLivroOferecido() : "Nenhum";

        Label lblTitulo = new Label("Seu livro: " + tituloSolicitado);
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #212529;");
        lblTitulo.setWrapText(true);

        Label lblProponente = new Label("Proposto por: " + proponenteNome);
        lblProponente.setStyle("-fx-font-size: 13px; -fx-text-fill: #6C757D;");
        lblProponente.setWrapText(true);

        Label lblOferecido = new Label("Livro oferecido: " + livroOferecido);
        lblOferecido.setStyle("-fx-font-size: 14px; -fx-text-fill: #2D6A4F; -fx-font-weight: bold;");
        lblOferecido.setWrapText(true);

        vBox.getChildren().addAll(lblTitulo, lblProponente, lblOferecido);
        HBox.setHgrow(vBox, Priority.ALWAYS);

        // Botões de Ação
        HBox actionsBox = new HBox();
        actionsBox.setSpacing(10.0);
        actionsBox.setAlignment(Pos.CENTER);

        Button btnAceitar = new Button("Aceitar");
        btnAceitar.setStyle("-fx-background-color: #2D6A4F; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 6 12 6 12; -fx-cursor: hand;");
        btnAceitar.setOnAction(e -> {
            // 1. Marca proposta como aceita
            propostaRepo.atualizarStatus(proposta, "ACEITA");
            
            // 2. Rejeita automaticamente outras propostas para o mesmo anuncio
            propostaRepo.rejeitarOutrasParaAnuncio(proposta.getAnuncio(), proposta);
            
            // 3. Registra a Transacao no banco
            String dataStr = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            Transacao transacao = new Transacao(
                "TROCA",
                tituloSolicitado,
                proposta.getAnuncio().getVendedor(),
                proposta.getProponente(),
                "Troca por: " + livroOferecido,
                dataStr
            );
            transacaoRepo.registrar(transacao);

            // 4. Remove o anuncio do catálogo
            anuncioRepo.removerAnuncio(proposta.getAnuncio());

            // 5. Exibe confirmação com detalhes de contato
            AlertHelper.showInfo(
                "Proposta Aceita",
                "Troca Confirmada com Sucesso!",
                "Você aceitou a proposta de troca do livro '" + tituloSolicitado + "' por '" + livroOferecido + "'.\n\n"
                + "Entre em contato com o proponente para combinar a troca:\n"
                + "Nome: " + proposta.getProponente().getNome() + "\n"
                + "Telefone: " + proposta.getProponente().getFone() + "\n"
                + "E-mail: " + proposta.getProponente().getEmail()
            );

            recarregarPropostas();
        });

        Button btnRejeitar = new Button("Rejeitar");
        btnRejeitar.setStyle("-fx-background-color: #DC3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 6 12 6 12; -fx-cursor: hand;");
        btnRejeitar.setOnAction(e -> {
            propostaRepo.atualizarStatus(proposta, "REJEITADA");
            
            AlertHelper.showInfo(
                "Proposta Rejeitada",
                "Proposta Rejeitada",
                "Você rejeitou a proposta de troca para o livro '" + tituloSolicitado + "'."
            );
            
            recarregarPropostas();
        });

        actionsBox.getChildren().addAll(btnAceitar, btnRejeitar);
        hBox.getChildren().addAll(imageView, vBox, actionsBox);
        return hBox;
    }
}
