package code;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.awt.*;

public class MyShape {
    GraphicsContext gc;
    Paint color;
    double startX;
    double startY;
    double endX;
    double endY;
    int brushWidth;

    public MyShape(GraphicsContext gc, double startX, double startY, double endX, double endY, Paint color, int brushWidth) {
        this.gc = gc;
        this.color = color;
        gc.setStroke(color);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.brushWidth = brushWidth;
    }

    public void draw(){}

    public void setColor(Paint c) {
        gc.setStroke(c);
    }
}
