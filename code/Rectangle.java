package code;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Rectangle extends MyShape {
    public Rectangle(GraphicsContext gc, double startX, double startY, double endX, double endY, Paint color, int brushWidth) {
        super(gc, startX, startY, endX, endY, color, brushWidth);
    }

    @Override
    public void draw() {
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        double topX = Math.min(startX, endX);
        double topY = Math.min(startY, endY);

        gc.setLineWidth(brushWidth);
        gc.strokeRect(topX, topY, width, height);
    }
}
