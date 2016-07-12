package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		//Load FXML-File

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/MainView.fxml"));
		GridPane rootLayout = loader.load();

		Scene scene = new Scene(rootLayout);

		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.getIcons().add(new Image("http://www.iconsfind.com/wp-content/uploads/2015/11/20151106_563c235abcd0c.png"));
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setWidth(360 + 6);
		primaryStage.setHeight(410 + 28);
		primaryStage.setTitle("Das Damenproblem");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
