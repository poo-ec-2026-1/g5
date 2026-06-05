package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblErro;
    
    @FXML private HBox containerEmail;
    @FXML private HBox containerSenha;

    @FXML
    public void initialize() {
        // Limpa erro ao digitar
        txtEmail.textProperty().addListener((obs, oldV, newV) -> clearError());
        txtSenha.textProperty().addListener((obs, oldV, newV) -> clearError());
        
        // Adiciona efeitos de foco interativos
        setupFocusEffect(txtEmail, containerEmail);
        setupFocusEffect(txtSenha, containerSenha);
    }
    
    private void setupFocusEffect(TextField field, HBox container) {
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                container.setStyle("-fx-border-color: #2D6A4F; -fx-border-width: 2; -fx-padding: 7 11 7 11;");
            } else {
                container.setStyle("");
            }
        });
    }

    @FXML
    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            showError("Preencha todos os campos.");
            return;
        }

        boolean autenticado = SessionManager.getInstance().login(email, senha);
        if (autenticado) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
                loader.setClassLoader(getClass().getClassLoader());
                Parent mainRoot = loader.load();
                txtEmail.getScene().setRoot(mainRoot);
            } catch (IOException e) {
                e.printStackTrace();
                showError("Erro ao carregar a tela principal.");
            }
        } else {
            showError("E-mail ou senha incorretos.");
        }
    }

    @FXML
    private void navToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegisterView.fxml"));
            loader.setClassLoader(getClass().getClassLoader());
            Parent registerRoot = loader.load();
            txtEmail.getScene().setRoot(registerRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String msg) {
        lblErro.setText(msg);
        lblErro.setVisible(true);
        lblErro.setManaged(true);
    }

    private void clearError() {
        lblErro.setVisible(false);
        lblErro.setManaged(false);
    }
}
