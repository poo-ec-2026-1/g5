 
	
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/View.fxml"));
			loader.setClassLoader(Main.class.getClassLoader());
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root,1050,850);
			scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.setMinWidth(800); // limite minimo largura
			primaryStage.setMinHeight(850); // limite minimo altura
			primaryStage.setResizable(true); // permitir maximizar e redimensionar
			primaryStage.setTitle("TradeLibrary - Troca e Venda de Livros Usados");
			
			Image icone = new Image(getClass().getResourceAsStream("/resources/images/librarylogo1.png"));

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
