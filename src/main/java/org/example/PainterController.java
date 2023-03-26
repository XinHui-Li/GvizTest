package org.example;

//PainterController.java


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class PainterController {
    // enum representing pen sizes
    private enum PenSize {
        SMALL(2), MEDIUM(4), LARGE(6);

        private final int radius;

        PenSize(int radius) {
            this.radius = radius;
        }

        public int getRadius() {
            return radius;
        }
    };

    private enum Shape {
        RECT, CIRCLE, LINE
    };

    // RGB三元色
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    // 初始化：粗细、颜色、形状
    private PenSize radius = PenSize.MEDIUM;
    private Paint brushColor = Color.BLACK;
    private Paint TRANSPARENT = Color.rgb(255,255,255,0.0);//透明色
    private Shape shape = Shape.LINE;
    //鼠标坐标
    private double mouseEnterX;
    private double mouseEnterY;
    private double mouseReleasedX;
    private double mouseReleasedY;
    private double rectangleWidth;
    private double rectangleHeight;
    private double circleCenterX;
    private double circleCenterY;
    private double circleRadius;
    @FXML
    private Button setColorButton;
    @FXML
    private RadioButton mediumRadioButton;
    @FXML
    private TextField greenTextField;
    @FXML
    private ToggleGroup sizeToggleGroup;
    @FXML
    private ToggleGroup shapeToggleGroup;
    @FXML
    private TextField redTextField;
    @FXML
    private RadioButton lineRadioButton;
    @FXML
    private RadioButton rectRadioButton;
    @FXML
    private Pane drawingAreaPane;
    @FXML
    private Button clearButton;
    @FXML
    private RadioButton largeRadioButton;
    @FXML
    private RadioButton smallRadioButton;
    @FXML
    private TextField blueTextField;
    @FXML
    private Button undoButton;
    @FXML
    private RadioButton circleRadioButton;

    public void initialize() {
        //形状按钮赋值
        lineRadioButton.setUserData(Shape.LINE);
        circleRadioButton.setUserData(Shape.CIRCLE);
        rectRadioButton.setUserData(Shape.RECT);
        //粗细按钮赋值
        smallRadioButton.setUserData(PenSize.SMALL);
        mediumRadioButton.setUserData(PenSize.MEDIUM);
        largeRadioButton.setUserData(PenSize.LARGE);
    }

    void getValueOfRgb() {
        red = Integer.parseInt(redTextField.getText());
        green = Integer.parseInt(greenTextField.getText());
        blue = Integer.parseInt(blueTextField.getText());
        brushColor = Color.rgb(red, green, blue);
    }
    @FXML
    void drawingAreaMousePressed(MouseEvent e) {
        mouseEnterX = e.getX();
        mouseEnterY = e.getY();
        if(mouseEnterX > drawingAreaPane.getWidth() || mouseEnterY > drawingAreaPane.getHeight())
            return;
    }

    @FXML
    void drawingAreaMouseReleased(MouseEvent e) {
        mouseReleasedX=e.getX();
        mouseReleasedY=e.getY();
        if(mouseReleasedX > drawingAreaPane.getWidth() || mouseReleasedX < 0 || mouseReleasedY > drawingAreaPane.getHeight()|| mouseReleasedY <0)
            return;
        getValueOfRgb();
        switch (shape) {
            case LINE:
                Line newLine = new Line(mouseEnterX, mouseEnterY, mouseReleasedX, mouseReleasedY);
                newLine.setStroke(brushColor);
                newLine.setStrokeWidth(radius.getRadius());
                drawingAreaPane.getChildren().add(newLine);
                break;
            case RECT:
                rectangleWidth = Math.abs(mouseReleasedX - mouseEnterX);
                rectangleHeight = Math.abs(mouseReleasedY - mouseEnterY);
                if(mouseEnterX > mouseReleasedX)
                    mouseEnterX = mouseReleasedX;
                if(mouseEnterY > mouseReleasedY)
                    mouseEnterY = mouseReleasedY;
                Rectangle newRect = new Rectangle(mouseEnterX, mouseEnterY, rectangleWidth, rectangleHeight);
                newRect.setStroke(brushColor);
                newRect.setFill(TRANSPARENT);
                newRect.setStrokeWidth(radius.getRadius());
                drawingAreaPane.getChildren().add(newRect);
                break;
            case CIRCLE:
                circleCenterX = (mouseEnterX + mouseReleasedX) / 2;
                circleCenterY = (mouseEnterY + mouseReleasedY) /2;
                circleRadius = Math.hypot((circleCenterX - mouseEnterX), (circleCenterY - mouseEnterY));
                Circle newCircle = new Circle(circleCenterX,circleCenterY,circleRadius);
                newCircle.setStroke(brushColor);
                newCircle.setFill(TRANSPARENT);
                newCircle.setStrokeWidth(radius.getRadius());
                drawingAreaPane.getChildren().add(newCircle);
                break;
        }
    }

    @FXML
    void sizeRadioButtonSelected(ActionEvent event) {
        radius = (PenSize) sizeToggleGroup.getSelectedToggle().getUserData();
    }


    @FXML
    void shapeRadioButtonSelected(ActionEvent event) {
        shape = (Shape) shapeToggleGroup.getSelectedToggle().getUserData();
        //shapeRadioButtonSelected
    }

    @FXML
    void undoButtonPressed(ActionEvent event) {
        int count = drawingAreaPane.getChildren().size();

        // if there are any shapes remove the last one added
        if (count > 0) {
            drawingAreaPane.getChildren().remove(count - 1);
        }
    }

    @FXML
    void clearButtonPressed(ActionEvent event) {
        drawingAreaPane.getChildren().clear(); // clear the canvas
    }
}