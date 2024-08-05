package code;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Square extends MyShape{
    public Square(GraphicsContext gc, double startX, double startY, double endX, double endY, Paint color, int brushWidth) {
        super(gc, startX, startY, endX, endY, color, brushWidth);
    }

    @Override
    public void draw() {
        double size = Math.min(Math.abs(endX - startX), Math.abs(endY - startY));
        double topX = startX;
        double topY = startY;
        if (endX < startX) {
            topX = startX - size;
        }
        if (endY < startY) {
            topY = startY - size;
        }

        gc.setLineWidth(brushWidth);
        gc.strokeRect(topX, topY, size, size);
    }
}
