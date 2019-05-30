import java.io.File;
import java.sql.Connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		
		System.out.println("Iniciando Conexão");
		try {
			Connection con = DBConnect.getConnection();
			System.out.println("Conexão estabelecida");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		File path = new File(".");
		File diretorio = new File(path.getAbsolutePath()+"/src");
		if(!diretorio.exists())
			diretorio.mkdir();
		
		Parent root = FXMLLoader.load(getClass().getResource("Tela.fxml"));
		Scene sc = new Scene(root,1000,600);
		stage.setScene(sc);
        stage.show();
	}
}
