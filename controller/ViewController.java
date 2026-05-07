package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ViewController {
	@FXML
	private VBox menuLateral;

	@FXML
	public void initialize() {
	    // Começa escondido e sem ocupar espaço no layout
	    menuLateral.setVisible(false);
	    menuLateral.setManaged(false);
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
}
