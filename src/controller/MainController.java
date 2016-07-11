package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Stack;

public class MainController {

	@FXML
	public Button continueButton;

	@FXML
	private Text attempts;
	@FXML
	private TextField delay;

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

	private StringProperty delayProperty = new SimpleStringProperty();

	private Stack<QueenController> remainingQueens = new Stack<>();
	private Stack<QueenController> placedQueens = new Stack<>();

	@FXML
	public void initialize() {
		//Initialize Queens
		initializeQueens();

		//Create Bindings
		attempts.textProperty().bind(attemptsProperty);

		delayProperty.set("30");
		delay.textProperty().bindBidirectional(delayProperty);
		delay.textProperty().addListener((observable, oldValue, newValue) -> {
			//Only accept Numbers
            if(!newValue.matches("\\d*")){
				delay.setText(oldValue);
			}else {
				//Can only continue if new input was a valid number
				continueButton.setDisable(newValue.length() == 0 || Integer.parseInt(newValue) < 22);
			}
        });
		delay.setTooltip(new Tooltip("Delay in ms"));
	}

	@FXML
	private void handleButtonAction(ActionEvent event) {
		//Background thread so the GUI isn't blocked
		new Thread(this::ContinueOrStartToPlaceQueens).start();
	}

	private void ContinueOrStartToPlaceQueens() {
		//Disable Input during runtime
		continueButton.setDisable(true);
		delay.setDisable(true);

		//Continue (Already valid solutions)
		if (remainingQueens.empty()) {
			QueenController latestQueen = placedQueens.pop();
			latestQueen.setColumn(latestQueen.getColumn() + 1);
			remainingQueens.push(latestQueen);
			latestQueen.setVisible(true);
		}

		while (!remainingQueens.empty()) {
			//Delay so the user gets more feedback during runtime
			try {
				Thread.sleep(Integer.parseInt(delayProperty.get()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TryPlaceNextQueen();
		}

		//Enable Input again
		continueButton.setDisable(false);
		delay.setDisable(false);
	}

	private void countAttempt()
	{
		amountOfAttempts++;
		if(amountOfAttempts % 10 == 0){
			setAmountOfAttempts(amountOfAttempts);
		}
	}

	private void TryPlaceNextQueen() {
		//Queen that has to be placed next
		QueenController currentQueen = remainingQueens.pop();

		//Make current queen visible
		currentQueen.setVisible(true);

		//First queen
		if(placedQueens.empty())
		{
			countAttempt();

			placedQueens.push(currentQueen);
			return;
		}

		//Try place queen until it's at the end
		while(currentQueen.getColumn() < 8)	{
			countAttempt();

			//Queen on same column
			boolean queenInSameColumn = placedQueens.stream().map(QueenController::getColumn).anyMatch(c -> currentQueen.getColumn() == c);

			//Queen on same diagonals
			boolean queenInSameLeftDiagonal = placedQueens.stream().map(q -> q.getColumn() - q.getRow()).anyMatch(c -> currentQueen.getColumn() - currentQueen.getRow() == c);
			boolean queenInSameRightDiagonal = placedQueens.stream().map(q -> q.getColumn() + q.getRow()).anyMatch(c -> currentQueen.getColumn() + currentQueen.getRow() == c);

			if(queenInSameColumn || queenInSameLeftDiagonal || queenInSameRightDiagonal) {
				//Can't place queen, so move her by one
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

		//Reset current queen for next try
		currentQueen.setColumn(0);

		//Hide queen again so only placed and current queen are visible
		currentQueen.setVisible(false);
		remainingQueens.push(currentQueen);

		//Move previous queen by one
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
