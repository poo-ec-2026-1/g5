package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/view/View.fxml"));
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.setResizable(false); // travar o tamanho da janela
			primaryStage.setTitle("TradeLibrary - Troca e Venda de Livros Usados");
			
			Image icone = new Image(getClass().getResourceAsStream("/application/resources/images/librarylogo1.png"));

			primaryStage.getIcons().add(icone); // adicionar icone
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
