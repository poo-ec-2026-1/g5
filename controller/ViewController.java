package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Usuario;
import model.Anuncio;

import java.io.IOException;

public class ViewController {
	private static ViewController instance;

	public static ViewController getInstance() {
		return instance;
	}

	@FXML
	private VBox menuLateral;
	
	@FXML
	private BorderPane contentArea;

	@FXML
	private Label lblUsuarioLogado;

	@FXML
	public void initialize() {
		instance = this;
	    // Começa escondido e sem ocupar espaço no layout
	    menuLateral.setVisible(false);
	    menuLateral.setManaged(false);
	    
	    // Exibe o usuário logado
	    if (lblUsuarioLogado != null) {
	        Usuario logado = SessionManager.getInstance().getUsuarioLogado();
	        if (logado != null) {
	            lblUsuarioLogado.setText(logado.getNome());
	        }
	    }
	    
	    // Carrega a tela inicial por padrão
	    navToHome();
	}

	public void mostrarDetalhesLivro(Anuncio anuncio) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookDetailsView.fxml"));
			BookDetailsController controller = new BookDetailsController(anuncio);
			loader.setController(controller);
			Parent view = loader.load();

			// Limpa todas as regiões do BorderPane
			contentArea.setTop(null);
			contentArea.setBottom(null);
			contentArea.setLeft(null);
			contentArea.setRight(null);

			contentArea.setCenter(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleLogout() {
	    SessionManager.getInstance().logout();
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
	        loader.setClassLoader(getClass().getClassLoader());
	        Parent loginRoot = loader.load();
	        lblUsuarioLogado.getScene().setRoot(loginRoot);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void toggleMenu() { // ao clicar no menu hamburguer
	    if (menuLateral.isVisible()) {
	        menuLateral.setVisible(false);
	        menuLateral.setManaged(false);
	    } else {
	        menuLateral.setVisible(true);
	        menuLateral.setManaged(true);
	    }
	}
	
	@FXML
	private void navToHome() {
	    loadView("/view/HomeView.fxml");
	}
	
	@FXML
	public void navToCatalog() {
	    loadView("/view/CatalogView.fxml");
	}
	
	@FXML
	private void navToAddBook() {
	    loadView("/view/AddBookView.fxml");
	}

	@FXML
	private void navToCart() {
	    loadView("/view/CartView.fxml");
	}
	
	private void loadView(String fxmlPath) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	        Parent view = loader.load();
	        
	        // Verifica se carregou a Home para ligar o botão ao controlador principal
	        if (fxmlPath.contains("HomeView.fxml")) {
	            javafx.scene.control.Button btn = (javafx.scene.control.Button) view.lookup("#btnCadastrarHome");
	            if (btn != null) {
	                btn.setOnAction(e -> navToAddBook());
	            }
	        }
	        
	        // Limpa todas as regiões do BorderPane para garantir que nada da Home fique sobrando
	        contentArea.setTop(null);
	        contentArea.setBottom(null);
	        contentArea.setLeft(null);
	        contentArea.setRight(null);
	        
	        // Coloca a nova tela no centro
	        contentArea.setCenter(view);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
