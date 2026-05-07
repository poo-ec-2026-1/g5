package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ViewController {
	@FXML
	private VBox menuLateral;
	
	@FXML
	private BorderPane contentArea;

	@FXML
	public void initialize() {
	    // Começa escondido e sem ocupar espaço no layout
	    menuLateral.setVisible(false);
	    menuLateral.setManaged(false);
	    
	    // Carrega a tela inicial por padrão
	    navToHome();
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
	private void navToCatalog() {
	    loadView("/view/CatalogView.fxml");
	}
	
	@FXML
	private void navToAddBook() {
	    loadView("/view/AddBookView.fxml");
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
