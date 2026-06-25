package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.io.IOException;
import model.Usuario;
import model.UsuarioRepository;

public class RegisterController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtFone;
    @FXML private PasswordField txtSenha;
    @FXML private PasswordField txtConfirmarSenha;

    @FXML private Label lblErroNome;
    @FXML private Label lblErroEmail;
    @FXML private Label lblErroFone;
    @FXML private Label lblErroSenha;
    @FXML private Label lblErroConfirmarSenha;
    @FXML private Label lblErroGeral;

    @FXML private HBox containerNome;
    @FXML private HBox containerEmail;
    @FXML private HBox containerFone;
    @FXML private HBox containerSenha;
    @FXML private HBox containerConfirmarSenha;

    @FXML
    public void initialize() {
        // Limpa erros ao digitar
        txtNome.textProperty().addListener((obs, oldV, newV) -> clearError(txtNome, lblErroNome, containerNome));
        txtEmail.textProperty().addListener((obs, oldV, newV) -> clearError(txtEmail, lblErroEmail, containerEmail));
        txtFone.textProperty().addListener((obs, oldV, newV) -> clearError(txtFone, lblErroFone, containerFone));
        txtSenha.textProperty().addListener((obs, oldV, newV) -> clearError(txtSenha, lblErroSenha, containerSenha));
        txtConfirmarSenha.textProperty().addListener((obs, oldV, newV) -> clearError(txtConfirmarSenha, lblErroConfirmarSenha, containerConfirmarSenha));

        // Adiciona efeitos de foco interativos
        setupFocusEffect(txtNome, containerNome);
        setupFocusEffect(txtEmail, containerEmail);
        setupFocusEffect(txtFone, containerFone);
        setupFocusEffect(txtSenha, containerSenha);
        setupFocusEffect(txtConfirmarSenha, containerConfirmarSenha);
    }

    private void setupFocusEffect(TextField field, HBox container) {
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                // Aplica borda verde apenas se não estiver em estado de erro
                if (!container.getStyleClass().contains("input-container-error")) {
                    container.setStyle("-fx-border-color: #2D6A4F; -fx-border-width: 2; -fx-padding: 7 11 7 11;");
                }
            } else {
                container.setStyle("");
            }
        });
    }

    @FXML
    private void handleRegister() {
        boolean valido = true;

        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String fone = txtFone.getText().trim();
        String senha = txtSenha.getText();
        String confirmarSenha = txtConfirmarSenha.getText();

        UsuarioRepository repository = SessionManager.getInstance().getUsuarioRepository();

        if (nome.isEmpty()) {
            showError(txtNome, lblErroNome, "O nome é obrigatório.", containerNome);
            valido = false;
        }

        if (email.isEmpty() || !email.contains("@")) {
            showError(txtEmail, lblErroEmail, "Digite um e-mail válido.", containerEmail);
            valido = false;
        } else if (repository.emailExiste(email)) {
            showError(txtEmail, lblErroEmail, "Este e-mail já está cadastrado.", containerEmail);
            valido = false;
        }

        if (fone.isEmpty()) {
            showError(txtFone, lblErroFone, "O telefone é obrigatório.", containerFone);
            valido = false;
        }

        if (senha.length() < 6) {
            showError(txtSenha, lblErroSenha, "A senha deve ter pelo menos 6 caracteres.", containerSenha);
            valido = false;
        }

        if (!confirmarSenha.equals(senha)) {
            showError(txtConfirmarSenha, lblErroConfirmarSenha, "As senhas não coincidem.", containerConfirmarSenha);
            valido = false;
        }

        if (valido) {
            // Aplica a criptografia na senha digitada
            String senhaCriptografada = SecurityUtils.hashSenha(senha);
            Usuario novoUsuario = new Usuario(nome, email, fone, senhaCriptografada);
            boolean cadastrado = repository.cadastrar(novoUsuario);

            if (cadastrado) {
                // ALERTA VISUAL: Sucesso no cadastro
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro Concluído");
                alert.setHeaderText(null);
                alert.setContentText("Seu usuário foi criado com sucesso! Bem-vindo ao TradeLibrary.");
                alert.showAndWait();

                // Realiza o login automático usando a senha criptografada
                SessionManager.getInstance().login(email, senhaCriptografada);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
                    loader.setClassLoader(getClass().getClassLoader());
                    Parent mainRoot = loader.load();
                    txtNome.getScene().setRoot(mainRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                    lblErroGeral.setText("Erro ao carregar a tela principal.");
                    lblErroGeral.setVisible(true);
                    lblErroGeral.setManaged(true);
                }
            } else {
                // ALERTA VISUAL: Erro se o banco de dados falhar
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Erro no Sistema");
                alert.setHeaderText("Não foi possível salvar os dados");
                alert.setContentText("Ocorreu uma falha interna ao tentar salvar o usuário. Por favor, tente novamente.");
                alert.showAndWait();

                lblErroGeral.setText("Erro ao salvar o usuário no banco de dados.");
                lblErroGeral.setVisible(true);
                lblErroGeral.setManaged(true);
            }
        } else {
            lblErroGeral.setText("Preencha todos os campos obrigatórios corretamente.");
            lblErroGeral.setVisible(true);
            lblErroGeral.setManaged(true);
        }
    }
    
    @FXML
    private void navToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            loader.setClassLoader(getClass().getClassLoader());
            Parent loginRoot = loader.load();
            txtNome.getScene().setRoot(loginRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(TextField field, Label labelErro, String msg, HBox container) {
        if (!container.getStyleClass().contains("input-container-error")) {
            container.getStyleClass().add("input-container-error");
        }
        container.setStyle(""); // Limpa estilo de foco dinâmico se houver erro
        labelErro.setText(msg);
        labelErro.setVisible(true);
        labelErro.setManaged(true);
    }

    private void clearError(TextField field, Label labelErro, HBox container) {
        container.getStyleClass().remove("input-container-error");
        labelErro.setVisible(false);
        labelErro.setManaged(false);
        lblErroGeral.setVisible(false);
        lblErroGeral.setManaged(false);
    }
}
