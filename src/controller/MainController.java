package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainController {

	@FXML
	private Text attempts;

	private StringProperty vorname = new SimpleStringProperty("Alexander");

	public void initialize() {
		attempts.textProperty().bind(vorname);
	}
}
