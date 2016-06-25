package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class MainController {

	@FXML
	private Text attempts;

	@FXML
	private Label A1;
	@FXML
	private Label A2;
	@FXML
	private Label A3;
	@FXML
	private Label A4;
	@FXML
	private Label A5;
	@FXML
	private Label A6;
	@FXML
	private Label A7;
	@FXML
	private Label A8;

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
	private StringProperty A1Property = new SimpleStringProperty();

	public void initialize() {

		attempts.textProperty().bind(attemptsProperty);

		setAmountOfAttempts(1);

		A1Property = new SimpleStringProperty("X");

		A1.textProperty().bind(A1Property);
	}
}
