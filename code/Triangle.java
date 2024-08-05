package code;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Triangle extends MyShape{
    double x1, y1, x2, y2, x3, y3;
    public Triangle(GraphicsContext gc, double startX, double startY, double endX, double endY, Paint color, int brushWidth) {
        super(gc, startX, startY, endX, endY, color, brushWidth);
        double base = endX - startX;
        double height = endY - startY;
        x1 = startX;
        x2 = startX + base;
        x3 = startX + base / 2;
        y1 = startY;
        y2 = startY;
        y3 = startY + height;
    }

    @Override
    public void draw() {
        double[] xPoints = {x1, x2, x3};
        double[] yPoints = {y1, y2, y3};

        gc.setLineWidth(brushWidth);
        gc.strokePolygon(xPoints, yPoints, 3);
    }
}
