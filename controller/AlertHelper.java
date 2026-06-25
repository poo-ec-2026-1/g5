package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AlertHelper {

    public static void showInfo(String title, String header, String content) {
        showAlert(AlertType.INFORMATION, title, header, content);
    }

    public static void showWarning(String title, String header, String content) {
        showAlert(AlertType.WARNING, title, header, content);
    }

    public static void showError(String title, String header, String content) {
        showAlert(AlertType.ERROR, title, header, content);
    }

    private static void showAlert(AlertType type, String title, String header, String content) {
        try {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);

            // Adiciona o logo da aplicação como ícone da janela (Stage)
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            try {
                Image icon = new Image(AlertHelper.class.getResourceAsStream("/resources/images/librarylogo1.png"));
                stage.getIcons().add(icon);
            } catch (Exception e) {
                System.err.println("Não foi possível carregar o ícone da janela: " + e.getMessage());
            }

            // Define o logo da aplicação como imagem gráfica do alerta (substitui o ícone padrão do JavaFX)
            try {
                Image logo = new Image(AlertHelper.class.getResourceAsStream("/resources/images/librarylogo1.png"));
                ImageView logoView = new ImageView(logo);
                logoView.setFitWidth(40);
                logoView.setFitHeight(40);
                logoView.setPreserveRatio(true);
                
                // Container com fundo escuro para destacar o logo branco
                StackPane logoContainer = new StackPane(logoView);
                logoContainer.setStyle("-fx-background-color: #1E1E1E; -fx-background-radius: 8; -fx-padding: 8;");
                alert.setGraphic(logoContainer);
            } catch (Exception e) {
                System.err.println("Não foi possível carregar a imagem do logo: " + e.getMessage());
            }

            // Aplica a folha de estilo customizada
            DialogPane dialogPane = alert.getDialogPane();
            try {
                String cssPath = AlertHelper.class.getResource("/view/application.css").toExternalForm();
                dialogPane.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.err.println("Não foi possível carregar o arquivo CSS para o alerta: " + e.getMessage());
            }
            dialogPane.getStyleClass().add("custom-alert");

            alert.showAndWait();
        } catch (Exception e) {
            // Fallback em caso de execução sem interface gráfica ou erro grave
            System.out.println("[" + title + "] " + header + " - " + content);
        }
    }
}
