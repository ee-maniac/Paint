package code;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Circle extends MyShape {
    public Circle(GraphicsContext gc, double startX, double startY, double endX, double endY, Paint color, int brushWidth) {
        super(gc, startX, startY, endX, endY, color, brushWidth);
    }

    @Override
    public void draw() {
        double radius = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)) / 2;
        double centerX = startX + (endX - startX) / 2;
        double centerY = startY + (endY - startY) / 2;

        gc.setLineWidth(brushWidth);
        gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }
}
