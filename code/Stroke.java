package code;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Stroke extends MyShape{
    public Stroke(GraphicsContext gc, double startX, double startY, double endX, double endY, Paint color, int brushWidth) {
        super(gc, startX, startY, endX, endY, color, brushWidth);
    }

    @Override
    public void draw() {
        gc.setLineWidth(brushWidth);
        gc.strokeLine(startX, startY, endX, endY);
    }
}
