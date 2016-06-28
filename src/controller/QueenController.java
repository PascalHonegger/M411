package controller;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class QueenController {

    private ImageView queen;

    public QueenController(ImageView queen)
    {
        this.queen = queen;
    }

    public int getColumn()
    {
        return GridPane.getColumnIndex(queen);
    }

    public void setColumn(int column)
    {
        GridPane.setColumnIndex(queen, column);
    }

    public int getRow()
    {
        return GridPane.getRowIndex(queen);
    }

    public void setRow(int row)
    {
        GridPane.setRowIndex(queen, row);
    }

    public void setVisible(boolean visible) { queen.setVisible(visible);}
}
