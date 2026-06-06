package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.*;

public class AddBookController {

    @FXML private TextField txtTitulo;
    @FXML private Label lblErroTitulo;
    
    @FXML private TextField txtAutor;
    @FXML private Label lblErroAutor;
    
    @FXML private TextField txtIsbn;
    @FXML private ComboBox<String> cmbEstado;
    
    @FXML private RadioButton rbVenda;
    @FXML private RadioButton rbTroca;
    @FXML private ToggleGroup tipoAnuncio;
    
    @FXML private VBox boxPreco;
    @FXML private TextField txtPreco;
    @FXML private Label lblErroPreco;
    
    @FXML private VBox boxProcura;
    @FXML private TextField txtProcura;
    @FXML private Label lblErroProcura;
    
    @FXML private TextArea txtDescricao;

    @FXML
    public void initialize() {
        // Inicializa o ComboBox de estados
        cmbEstado.getItems().addAll("Novo", "Seminovo", "Usado");
        cmbEstado.getSelectionModel().selectFirst();
        
        // Listener para trocar entre Venda e Troca
        tipoAnuncio.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (rbVenda.isSelected()) {
                boxPreco.setVisible(true);
                boxPreco.setManaged(true);
                
                boxProcura.setVisible(false);
                boxProcura.setManaged(false);
            } else {
                boxPreco.setVisible(false);
                boxPreco.setManaged(false);
                
                boxProcura.setVisible(true);
                boxProcura.setManaged(true);
            }
        });
        
        // Listeners para limpar os erros ao digitar
        txtTitulo.textProperty().addListener((obs, oldV, newV) -> limparErro(txtTitulo, lblErroTitulo));
        txtAutor.textProperty().addListener((obs, oldV, newV) -> limparErro(txtAutor, lblErroAutor));
        txtPreco.textProperty().addListener((obs, oldV, newV) -> limparErro(txtPreco, lblErroPreco));
        txtProcura.textProperty().addListener((obs, oldV, newV) -> limparErro(txtProcura, lblErroProcura));
    }

    @FXML
    private void salvarAnuncio() {
        boolean formValido = validarFormulario();
        
        if (formValido) {
            Usuario vendedor = SessionManager.getInstance().getUsuarioLogado();
            if (vendedor == null) {
                System.out.println("Erro: Nenhum usuário logado na sessão.");
                return;
            }

            Livro livro = new Livro(
                txtTitulo.getText().trim(),
                txtAutor.getText().trim(),
                txtIsbn.getText() != null ? txtIsbn.getText().trim() : "",
                cmbEstado.getValue()
            );

            AnuncioRepository repo = new AnuncioRepository();
            boolean salvo = false;

            if (rbVenda.isSelected()) {
                double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
                AnuncioVenda anuncioVenda = new AnuncioVenda(
                    livro,
                    vendedor,
                    preco,
                    txtDescricao.getText() != null ? txtDescricao.getText().trim() : ""
                );
                salvo = repo.cadastrarAnuncioVenda(anuncioVenda);
            } else {
                AnuncioTroca anuncioTroca = new AnuncioTroca(
                    livro,
                    vendedor,
                    txtProcura.getText().trim(),
                    txtDescricao.getText() != null ? txtDescricao.getText().trim() : ""
                );
                salvo = repo.cadastrarAnuncioTroca(anuncioTroca);
            }

            if (salvo) {
                System.out.println("Anúncio salvo com sucesso no banco de dados!");
                limparCampos();
            } else {
                System.out.println("Erro ao salvar o anúncio no banco de dados.");
            }
        }
    }

    private void limparCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtIsbn.clear();
        txtPreco.clear();
        txtProcura.clear();
        txtDescricao.clear();
        cmbEstado.getSelectionModel().selectFirst();
    }
    
    private boolean validarFormulario() {
        boolean valido = true;
        
        // Validar Título
        if (txtTitulo.getText() == null || txtTitulo.getText().trim().isEmpty()) {
            mostrarErro(txtTitulo, lblErroTitulo, "O título do livro é obrigatório.");
            valido = false;
        }
        
        // Validar Autor
        if (txtAutor.getText() == null || txtAutor.getText().trim().isEmpty()) {
            mostrarErro(txtAutor, lblErroAutor, "O autor é obrigatório.");
            valido = false;
        }
        
        // Validar baseado no tipo de anúncio
        if (rbVenda.isSelected()) {
            try {
                double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
                if (preco <= 0) {
                    mostrarErro(txtPreco, lblErroPreco, "O preço deve ser maior que zero.");
                    valido = false;
                }
            } catch (NumberFormatException e) {
                mostrarErro(txtPreco, lblErroPreco, "Preço inválido. Digite apenas números.");
                valido = false;
            }
        } else {
            if (txtProcura.getText() == null || txtProcura.getText().trim().isEmpty()) {
                mostrarErro(txtProcura, lblErroProcura, "Especifique o que você procura.");
                valido = false;
            }
        }
        
        return valido;
    }
    
    private void mostrarErro(TextField campo, Label labelErro, String mensagem) {
        if (!campo.getStyleClass().contains("input-error")) {
            campo.getStyleClass().add("input-error");
        }
        labelErro.setText(mensagem);
        labelErro.setVisible(true);
        labelErro.setManaged(true);
    }
    
    private void limparErro(TextField campo, Label labelErro) {
        campo.getStyleClass().remove("input-error");
        labelErro.setVisible(false);
        labelErro.setManaged(false);
    }
}
