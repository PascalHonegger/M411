package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Stack;

public class MainController {

	@FXML
	public Button continueButton;

	@FXML
	private Text attempts;

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

	private Stack<QueenController> remainingQueens = new Stack<>();
	private Stack<QueenController> placedQueens = new Stack<>();

	public void initialize() {
		initializeQueens();

		attempts.textProperty().bind(attemptsProperty);
	}

	@FXML
	private void handleButtonAction(ActionEvent event) {
		//Background thread so the GUI isn't blocked

		new Thread(this::ContinueOrStartToPlaceQueens).start();
	}

	private void ContinueOrStartToPlaceQueens() {
		continueButton.setDisable(true);

		if (remainingQueens.empty()) {
			QueenController latestQueen = placedQueens.pop();
			latestQueen.setColumn(latestQueen.getColumn() + 1);
			remainingQueens.push(latestQueen);
		}

		while (!remainingQueens.empty()) {
			TryPlaceNextQueen();
		}

		continueButton.setDisable(false);
	}

	private void countAttempt()
	{
		amountOfAttempts++;
		setAmountOfAttempts(amountOfAttempts);
	}

	private void TryPlaceNextQueen() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		QueenController currentQueen = remainingQueens.pop();

		if(placedQueens.empty())
		{
			countAttempt();

			placedQueens.push(currentQueen);
			return;
		}

		//Try place queen
		while(currentQueen.getColumn() < 8)	{
			countAttempt();

			//Queen on same column
			boolean queenInSameColumn = placedQueens.stream().map(q -> q.getColumn()).anyMatch(c -> currentQueen.getColumn() == c);
			boolean queenInSameLeftDiagonal = placedQueens.stream().map(q -> q.getColumn() - q.getRow()).anyMatch(c -> currentQueen.getColumn() - currentQueen.getRow() == c);
			boolean queenInSameRightDiagonal = placedQueens.stream().map(q -> q.getColumn() + q.getRow()).anyMatch(c -> currentQueen.getColumn() + currentQueen.getRow() == c);

			if(queenInSameColumn || queenInSameLeftDiagonal || queenInSameRightDiagonal) {
				currentQueen.setColumn(currentQueen.getColumn() + 1);
			}
			else {
				//Can place queen
				placedQueens.push(currentQueen);
				return;
			}
		}

		//Couldn't place queen!

		if(currentQueen.getRow() == 8){
			initialize();
			return;
		}

		currentQueen.setColumn(0);
		remainingQueens.push(currentQueen);
		QueenController oldQueen = placedQueens.pop();
		oldQueen.setColumn(oldQueen.getColumn() + 1);
		remainingQueens.push(oldQueen);
	}

	private void initializeQueens() {
		remainingQueens.push(new QueenController(queen8));
		remainingQueens.push(new QueenController(queen7));
		remainingQueens.push(new QueenController(queen6));
		remainingQueens.push(new QueenController(queen5));
		remainingQueens.push(new QueenController(queen4));
		remainingQueens.push(new QueenController(queen3));
		remainingQueens.push(new QueenController(queen2));
		remainingQueens.push(new QueenController(queen1));
	}
}
