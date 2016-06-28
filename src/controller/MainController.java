package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class MainController {

	@FXML
	private Text attempts;

	@FXML
	private GridPane chessboard;

	@FXML
	private ImageView queen1;
	@FXML
	private ImageView queen2;
	@FXML
	private ImageView queen3;
	@FXML
	private ImageView queen4;
	@FXML
	private ImageView queen5;
	@FXML
	private ImageView queen6;
	@FXML
	private ImageView queen7;
	@FXML
	private ImageView queen8;

	private int amountOfAttempts = 0;

	private String getAmountOfAttempts()
	{
		return Integer.toString(amountOfAttempts);
	}

	private void setAmountOfAttempts(int value)
	{
		amountOfAttempts = value;
		attemptsProperty.set(getAmountOfAttempts());
	}

	private StringProperty attemptsProperty = new SimpleStringProperty();

	public void initialize() {

		attempts.textProperty().bind(attemptsProperty);

		setAmountOfAttempts(1);
	}
}
