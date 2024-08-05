package code;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class CanvasController {
    private double startX, startY, newX, newY;
    private Pane selectedShape;
    private ArrayList<MyShape> shapes = new ArrayList<>();
    private MyShape lastShape = null;
    private Paint color = Color.BLACK;
    private int brushWidth = 1;
    private boolean showResize = false;
    private boolean linked = true;

    @FXML
    BorderPane rootPane;
    @FXML
    Pane pane;
    @FXML
    Canvas canvas;
    @FXML
    Pane trianglePane;
    @FXML
    Pane squarePane;
    @FXML
    Pane circlePane;
    @FXML
    Pane rectanglePane;
    @FXML
    Pane ellipsePane;
    @FXML
    ComboBox<String> brushComboBox;
    @FXML
    ImageView rotateIcon;
    @FXML
    ImageView mirrorIcon;
    @FXML
    ImageView resizeIcon;
    @FXML
    Pane resizePane;
    @FXML
    Button cancelButton;
    @FXML
    Button OKButton;
    @FXML
    TextField hField;
    @FXML
    TextField vField;

    public void initialize() {
        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        resizePane.setVisible(showResize);

        brushComboBox.setValue("1px");
        brushComboBox.valueProperty().addListener((observable, oldValue, newValue) -> changeBrushWidth(oldValue, newValue));

        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            startX = e.getX();
            startY = e.getY();
            newX = startX;
            newY = startY;

            if(lastShape != null) {
                if(!isMouseInsideShape(e, lastShape) && isWithinEdges(lastShape, e.getX(), e.getY()) == 5) {
                    redrawShapes();
                    lastShape = null;
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if(lastShape != null) {
                if(isMouseInsideShape(e, lastShape)) {
                    if(isWithinEdges(lastShape, e.getX(), e.getY()) == 5) {
                        moveShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY);
                    }
                    else if(isWithinEdges(lastShape, e.getX(), e.getY()) == 1) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 1);
                    }
                    else if(isWithinEdges(lastShape, e.getX(), e.getY()) == 2) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 2);
                    }
                    else if(isWithinEdges(lastShape, e.getX(), e.getY()) == 3) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 3);
                    }
                    else if(isWithinEdges(lastShape, e.getX(), e.getY()) == 4) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 4);
                    }

                    newX = e.getX();
                    newY = e.getY();
                }
                else if (lastShape instanceof Triangle && isWithinEdges(lastShape, e.getX(), e.getY()) != 5) {
                    if(isWithinEdges(lastShape, e.getX(), e.getY()) == 1) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 1);
                    }
                    else if(isWithinEdges(lastShape, e.getX(), e.getY()) == 2) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 2);
                    }
                    else if(isWithinEdges(lastShape, e.getX(), e.getY()) == 3) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 3);
                    }
                    else if(isWithinEdges(lastShape, e.getX(), e.getY()) == 4) {
                        resizeShapeOnDrag(lastShape, e.getX()-newX, e.getY()-newY, 4);
                    }

                    newX = e.getX();
                    newY = e.getY();
                }
            }

            double endX = e.getX();
            double endY = e.getY();

            if(lastShape == null) {
                if (selectedShape == trianglePane || selectedShape == squarePane ||
                        selectedShape == circlePane || selectedShape == rectanglePane ||
                        selectedShape == ellipsePane) {

                    redrawShapes();

                    if (selectedShape == trianglePane) {
                        Triangle triangle = new Triangle(gc, startX, startY, endX, endY, color, brushWidth);
                        triangle.draw();
                    } else if (selectedShape == squarePane) {
                        Square square = new Square(gc, startX, startY, endX, endY, color, brushWidth);
                        square.draw();
                    } else if (selectedShape == circlePane) {
                        Circle circle = new Circle(gc, startX, startY, endX, endY, color, brushWidth);
                        circle.draw();
                    } else if (selectedShape == rectanglePane) {
                        Rectangle rectangle = new Rectangle(gc, startX, startY, endX, endY, color, brushWidth);
                        rectangle.draw();
                    } else if (selectedShape == ellipsePane) {
                        Ellipse ellipse = new Ellipse(gc, startX, startY, endX, endY, color, brushWidth);
                        ellipse.draw();
                    }
                } else {
                    Stroke stroke = new Stroke(gc, startX, startY, endX, endY, color, brushWidth);
                    stroke.draw();
                    shapes.add(stroke);
                    startX = endX;
                    startY = endY;
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            double endX = e.getX();
            double endY = e.getY();

            if(lastShape == null) {
                if (selectedShape == trianglePane || selectedShape == squarePane ||
                        selectedShape == circlePane || selectedShape == rectanglePane ||
                        selectedShape == ellipsePane) {

                    if (selectedShape == trianglePane) {
                        Triangle triangle = new Triangle(gc, startX, startY, endX, endY, color, brushWidth);
                        triangle.draw();
                        shapes.add(triangle);
                        lastShape = triangle;
                    } else if (selectedShape == squarePane) {
                        Square square = new Square(gc, startX, startY, endX, endY, color, brushWidth);
                        square.draw();
                        shapes.add(square);
                        lastShape = square;
                    } else if (selectedShape == circlePane) {
                        Circle circle = new Circle(gc, startX, startY, endX, endY, color, brushWidth);
                        circle.draw();
                        shapes.add(circle);
                        lastShape = circle;
                    } else if (selectedShape == rectanglePane) {
                        Rectangle rectangle = new Rectangle(gc, startX, startY, endX, endY, color, brushWidth);
                        rectangle.draw();
                        shapes.add(rectangle);
                        lastShape = rectangle;
                    } else if (selectedShape == ellipsePane) {
                        Ellipse ellipse = new Ellipse(gc, startX, startY, endX, endY, color, brushWidth);
                        ellipse.draw();
                        shapes.add(ellipse);
                        lastShape = ellipse;
                    }

                    drawTemporaryRectangle(lastShape);
                }
            }
        });

        rootPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                shapes.clear();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
            if (lastShape != null) {
                switch (event.getCode()) {
                    case R -> rotateShape(lastShape);
                    case H -> flipShapeHorizontal(lastShape);
                    case V -> flipShapeVertical(lastShape);
                    case D -> lastShape = null;
                }
            }
        });

        rotateIcon.setOnMouseClicked(event -> {
            if (lastShape != null) {
                rotateShape(lastShape);
            }
        });

        mirrorIcon.setOnMouseClicked(event -> {
            if (lastShape != null) {
                flipShapeHorizontal(lastShape);
            }
        });

        resizeIcon.setOnMouseClicked(event -> {
            if(!showResize) {
                showResize = true;
                resizePane.setVisible(showResize);
                if(lastShape != null) {
                    getShapeSize(lastShape);
                }
            }
            else {
                showResize = false;
                resizePane.setVisible(showResize);
            }
        });

        OKButton.setOnMouseClicked(event -> {
            if(showResize && lastShape != null) {
                resizeShape(lastShape);
                showResize = false;
                resizePane.setVisible(showResize);
            }
        });

        cancelButton.setOnMouseClicked(event -> {
            if(showResize) {
                showResize = false;
                resizePane.setVisible(showResize);
            }
        });

        addEventHandlers(trianglePane);
        addEventHandlers(squarePane);
        addEventHandlers(circlePane);
        addEventHandlers(rectanglePane);
        addEventHandlers(ellipsePane);
    }

    private void addEventHandlers(Pane shapePane) {
        shapePane.setOnMouseEntered(e -> {
            if (selectedShape != shapePane) {
                shapePane.getStyleClass().add("shape-hover");
            }
        });

        shapePane.setOnMouseExited(e -> shapePane.getStyleClass().remove("shape-hover"));

        shapePane.setOnMouseClicked(e -> {
            if (selectedShape != null) {
                selectedShape.getStyleClass().remove("shape-selected");
            }
            if (selectedShape == shapePane) {
                selectedShape = null;
            } else {
                shapePane.getStyleClass().add("shape-selected");
                selectedShape = shapePane;
            }
        });
    }

    public <T extends MyShape> void rotateShape(T shape) {
        if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            rotateTriangle(triangle);
        } else {
            double centerX = (shape.startX + shape.endX) / 2;
            double centerY = (shape.startY + shape.endY) / 2;

            double newStartX = centerX - (shape.endY - centerY);
            double newStartY = centerY + (shape.startX - centerX);
            double newEndX = centerX - (shape.startY - centerY);
            double newEndY = centerY + (shape.endX - centerX);

            shape.startX = newStartX;
            shape.startY = newStartY;
            shape.endX = newEndX;
            shape.endY = newEndY;
        }

        redrawShapes();
        drawTemporaryRectangle(lastShape);
    }

    private void rotateTriangle(Triangle triangle) {
        double centerX = (triangle.x1 + triangle.x2 + triangle.x3) / 3;
        double centerY = (triangle.y1 + triangle.y2 + triangle.y3) / 3;

        double[] newPoint1 = rotatePoint(triangle.x1, triangle.y1, centerX, centerY);
        double[] newPoint2 = rotatePoint(triangle.x2, triangle.y2, centerX, centerY);
        double[] newPoint3 = rotatePoint(triangle.x3, triangle.y3, centerX, centerY);

        triangle.x1 = newPoint1[0];
        triangle.y1 = newPoint1[1];
        triangle.x2 = newPoint2[0];
        triangle.y2 = newPoint2[1];
        triangle.x3 = newPoint3[0];
        triangle.y3 = newPoint3[1];
    }

    private double[] rotatePoint(double x, double y, double centerX, double centerY) {
        double relativeX = x - centerX;
        double relativeY = y - centerY;
        double newRelativeX = -relativeY;
        double newRelativeY = relativeX;
        double newX = newRelativeX + centerX;
        double newY = newRelativeY + centerY;
        return new double[]{newX, newY};
    }

    public <T extends MyShape> void flipShapeHorizontal(T shape) {
        if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            flipTriangleHorizontal(triangle);
        } else {
            double centerX = (shape.startX + shape.endX) / 2;

            double newStartX = 2 * centerX - shape.startX;
            double newEndX = 2 * centerX - shape.endX;

            shape.startX = newStartX;
            shape.endX = newEndX;
        }

        redrawShapes();
        drawTemporaryRectangle(lastShape);
    }

    public <T extends MyShape> void flipShapeVertical(T shape) {
        if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            flipTriangleVertical(triangle);
        } else {
            double centerY = (shape.startY + shape.endY) / 2;

            double newStartY = 2 * centerY - shape.startY;
            double newEndY = 2 * centerY - shape.endY;

            shape.startY = newStartY;
            shape.endY = newEndY;
        }

        redrawShapes();
        drawTemporaryRectangle(lastShape);
    }

    private void flipTriangleHorizontal(Triangle triangle) {
        double centerX = (triangle.x1 + triangle.x2 + triangle.x3) / 3;

        triangle.x1 = 2 * centerX - triangle.x1;
        triangle.x2 = 2 * centerX - triangle.x2;
        triangle.x3 = 2 * centerX - triangle.x3;
    }

    private void flipTriangleVertical(Triangle triangle) {
        double centerY = (triangle.y1 + triangle.y2 + triangle.y3) / 3;

        triangle.y1 = 2 * centerY - triangle.y1;
        triangle.y2 = 2 * centerY - triangle.y2;
        triangle.y3 = 2 * centerY - triangle.y3;
    }

    private void redrawShapes() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (MyShape shape : shapes) {
            shape.draw();
        }
    }

    private <T extends MyShape> boolean isMouseInsideShape(MouseEvent event, T shape) {
        if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            return isMouseInsideTriangle(event, triangle);
        } else {
            double minX = Math.min(shape.startX, shape.endX);
            double minY = Math.min(shape.startY, shape.endY);
            double maxX = Math.max(shape.startX, shape.endX);
            double maxY = Math.max(shape.startY, shape.endY);

            return event.getX() > minX && event.getX() < maxX &&
                    event.getY() > minY && event.getY() < maxY;
        }
    }

    private boolean isMouseInsideTriangle(MouseEvent event, Triangle triangle) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        double areaOrig = calculateTriangleArea(triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3);

        double area1 = calculateTriangleArea(mouseX, mouseY, triangle.x2, triangle.y2, triangle.x3, triangle.y3);
        double area2 = calculateTriangleArea(triangle.x1, triangle.y1, mouseX, mouseY, triangle.x3, triangle.y3);
        double area3 = calculateTriangleArea(triangle.x1, triangle.y1, triangle.x2, triangle.y2, mouseX, mouseY);

        return areaOrig == (area1 + area2 + area3);
    }

    private double calculateTriangleArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2)) / 2.0);
    }


    public <T extends MyShape> void moveShapeOnDrag(T shape, double offsetX, double offsetY) {
        if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            moveTriangleOnDrag(triangle, offsetX, offsetY);
        } else {
            shape.startX += offsetX;
            shape.startY += offsetY;
            shape.endX += offsetX;
            shape.endY += offsetY;
        }

        redrawShapes();
        drawTemporaryRectangle(lastShape);
    }

    private void moveTriangleOnDrag(Triangle triangle, double offsetX, double offsetY) {
        triangle.x1 += offsetX;
        triangle.x2 += offsetX;
        triangle.x3 += offsetX;
        triangle.y1 += offsetY;
        triangle.y2 += offsetY;
        triangle.y3 += offsetY;
    }

    private <T extends MyShape> void drawTemporaryRectangle(T shape) {
        double minX;
        double minY;
        double width;
        double height;

        if (shape instanceof Circle) {
            double size = Math.sqrt(Math.pow(shape.endX - shape.startX, 2) + Math.pow(shape.endY - shape.startY, 2));
            width = size;
            height = size;
            minX = (shape.startX+shape.endX)/2-size/2;
            minY = (shape.startY+shape.endY)/2-size/2;
        }
        else if (shape instanceof Square) {
            double size = Math.min(Math.abs(shape.endX - shape.startX), Math.abs(shape.endY - shape.startY));
            width = size;
            height = size;
            minX = shape.startX;
            minY = shape.startY;
            if (shape.endX < startX) {
                minX = startX - size;
            }
            if (shape.endY < startY) {
                minY = startY - size;
            }
        }
        else if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;

            minX = Math.min(Math.min(triangle.x1, triangle.x2), triangle.x3);
            minY = Math.min(Math.min(triangle.y1, triangle.y2), triangle.y3);
            double maxX = Math.max(Math.max(triangle.x1, triangle.x2), triangle.x3);
            double maxY = Math.max(Math.max(triangle.y1, triangle.y2), triangle.y3);

            width = maxX - minX;
            height = maxY - minY;
        }
        else {
            minX = Math.min(shape.startX, shape.endX);
            minY = Math.min(shape.startY, shape.endY);
            width = Math.abs(shape.endX - shape.startX);
            height = Math.abs(shape.endY - shape.startY);
        }

        shape.gc.setFill(Color.BLUE);
        shape.gc.fillOval(minX - 10 - brushWidth, minY - 10 - brushWidth, 10 * 2, 10 * 2);
        shape.gc.fillOval(minX - 10 - brushWidth, minY+height - 10 + brushWidth, 10 * 2, 10 * 2);
        shape.gc.fillOval(minX+width - 10 + brushWidth, minY - 10 - brushWidth, 10 * 2, 10 * 2);
        shape.gc.fillOval(minX+width - 10 + brushWidth, minY+height - 10 + brushWidth, 10 * 2, 10 * 2);

        shape.gc.setStroke(Color.BLUE);
        shape.gc.setLineWidth(1);
        shape.gc.strokeRect(minX-brushWidth, minY-brushWidth, width+2*brushWidth, height+2*brushWidth);
        shape.gc.setStroke(color);
    }

    private <T extends MyShape> int isWithinEdges(T shape, double cordX, double cordY) {
        double minX;
        double minY;
        double width;
        double height;

        if (shape instanceof Circle) {
            double size = Math.sqrt(Math.pow(shape.endX - shape.startX, 2) + Math.pow(shape.endY - shape.startY, 2));
            width = size;
            height = size;
            minX = (shape.startX+shape.endX)/2-size/2;
            minY = (shape.startY+shape.endY)/2-size/2;
        }
        else if (shape instanceof Square) {
            double size = Math.min(Math.abs(shape.endX - shape.startX), Math.abs(shape.endY - shape.startY));
            width = size;
            height = size;
            minX = shape.startX;
            minY = shape.startY;
            if (shape.endX < startX) {
                minX = startX - size;
            }
            if (shape.endY < startY) {
                minY = startY - size;
            }
        }
        else if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;

            minX = Math.min(Math.min(triangle.x1, triangle.x2), triangle.x3);
            minY = Math.min(Math.min(triangle.y1, triangle.y2), triangle.y3);
            double maxX = Math.max(Math.max(triangle.x1, triangle.x2), triangle.x3);
            double maxY = Math.max(Math.max(triangle.y1, triangle.y2), triangle.y3);

            width = maxX - minX;
            height = maxY - minY;
        }
        else {
            minX = Math.min(shape.startX, shape.endX);
            minY = Math.min(shape.startY, shape.endY);
            width = Math.abs(shape.endX - shape.startX);
            height = Math.abs(shape.endY - shape.startY);
        }

        double maxX = minX  + width;
        double maxY = minY  + height;

        double s1 = Math.sqrt((cordX - maxX)*(cordX - maxX) + (cordY - minY)*(cordY - minY));
        double s2 = Math.sqrt((cordX - minX)*(cordX - minX) + (cordY - minY)*(cordY - minY));
        double s3 = Math.sqrt((cordX - minX)*(cordX - minX) + (cordY - maxY)*(cordY - maxY));
        double s4 = Math.sqrt((cordX - maxX)*(cordX - maxX) + (cordY - maxY)*(cordY - maxY));

        if(s1 < 10) {
            return 1;
        }
        if(s2 < 10) {
            return 2;
        }
        if(s3 < 10) {
            return 3;
        }
        if(s4 < 10) {
            return 4;
        }
        return 5;
    }

    private <T extends MyShape> void resizeShapeOnDrag(T shape, double offsetX, double offsetY, int type) {
        if (shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            resizeTriangleOnDrag(triangle, offsetX, offsetY, type);
        }
        else if(type == 1) {
            shape.startY += offsetY;
            shape.endX += offsetX;
        }
        else if(type == 2) {
            shape.startY += offsetY;
            shape.startX += offsetX;
        }
        else if(type == 3) {
            shape.endY += offsetY;
            shape.startX += offsetX;
        }
        else if(type == 4) {
            shape.endY += offsetY;
            shape.endX += offsetX;
        }

        redrawShapes();
        drawTemporaryRectangle(lastShape);
    }

    private void resizeTriangleOnDrag(Triangle triangle, double offsetX, double offsetY, int type) {
        if(type == 1) {
            triangle.x2 += offsetX;
            triangle.x3 += offsetX/2;
            if(triangle.y3 < triangle.y2) {
                triangle.y3 += offsetY;
            }
            else {
                triangle.y2 += offsetY;
                triangle.y1 += offsetY;
            }
        }
        else if(type == 2) {
            triangle.x1 += offsetX;
            triangle.x3 += offsetX/2;
            if(triangle.y3 < triangle.y1) {
                triangle.y3 += offsetY;
            }
            else {
                triangle.y1 += offsetY;
                triangle.y2 += offsetY;
            }
        }
        else if(type == 3) {
            triangle.x1 += offsetX;
            triangle.x3 += offsetX/2;
            if(triangle.y3 < triangle.y1) {
                triangle.y1 += offsetY;
                triangle.y2 += offsetY;
            }
            else {
                triangle.y3 += offsetY;
            }
        }
        else if(type == 4) {
            triangle.x2 += offsetX;
            triangle.x3 += offsetX/2;
            if(triangle.y3 < triangle.y2) {
                triangle.y2 += offsetY;
                triangle.y1 += offsetY;
            }
            else {
                triangle.y3 += offsetY;
            }
        }

        redrawShapes();
        drawTemporaryRectangle(lastShape);
    }

    private void changeBrushWidth(String oldValue, String newValue) {
        if(!oldValue.equals(newValue)) {
            brushWidth = Integer.parseInt(String.valueOf(newValue.charAt(0)));
            if (lastShape != null) {
                lastShape.brushWidth = brushWidth;
            }
        }

        redrawShapes();
        drawTemporaryRectangle(lastShape);
    }

    private <T extends MyShape> void getShapeSize(T shape) {
        if(shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            if(triangle.y1 == triangle.y2) {
                vField.setText(Double.toString(Math.abs(triangle.y3 - triangle.y1)));
                hField.setText(Double.toString(Math.abs(triangle.x1 - triangle.x2)));
            }
            else {
                vField.setText(Double.toString(Math.abs(triangle.y2 - triangle.y1)));
                hField.setText(Double.toString(Math.abs(triangle.x1 - triangle.x3)));
            }
        }
        else {
            vField.setText(Double.toString(Math.abs(shape.startY - shape.endY)));
            hField.setText(Double.toString(Math.abs(shape.startX - shape.endX)));
        }
    }

    private <T extends MyShape> void resizeShape(T shape) {
        if(shape instanceof Triangle) {
            Triangle triangle = (Triangle) shape;
            if(triangle.y1 == triangle.y2) {
                double a = Math.abs(triangle.y3 - triangle.y1);
                double b = Double.parseDouble(vField.getText()) - a;
                if(triangle.y3 > triangle.y1) {
                    triangle.y3 += b/2;
                    triangle.y1 -= b/2;
                    triangle.y2 -= b/2;
                }
                else {
                    triangle.y3 -= b/2;
                    triangle.y1 += b/2;
                    triangle.y2 += b/2;
                }

                double c = Math.abs(triangle.x1 - triangle.x2);
                double d = Double.parseDouble(hField.getText()) - c;
                if(triangle.x1 > triangle.x2) {
                    triangle.x1 += d/2;
                    triangle.x2 -= d/2;
                }
                else {
                    triangle.x1 -= d/2;
                    triangle.x2 += d/2;
                }
            }
            else {
                double a = Math.abs(triangle.y2 - triangle.y1);
                double b = Double.parseDouble(vField.getText()) - a;
                if(triangle.y2 > triangle.y1) {
                    triangle.y1 -= b/2;
                    triangle.y2 += b/2;
                }
                else {
                    triangle.y1 += b/2;
                    triangle.y2 -= b/2;
                }

                double c = Math.abs(triangle.x3 - triangle.x1);
                double d = Double.parseDouble(hField.getText()) - c;
                if(triangle.x3 > triangle.x1) {
                    triangle.x1 -= d/2;
                    triangle.x2 -= d/2;
                    triangle.x3 += d/2;
                }
                else {
                    triangle.x1 += d/2;
                    triangle.x2 += d/2;
                    triangle.x3 -= d/2;
                }
            }
        }
        else {
            double a = Math.abs(shape.startY - shape.endY);
            double b = Double.parseDouble(vField.getText()) - a;
            if(shape.startY < shape.endY) {
                shape.endY += b/2;
                shape.startY -= b/2;
            }
            else {
                shape.endY -= b/2;
                shape.startY += b/2;
            }

            double c = Math.abs(shape.startX - shape.endX);
            double d = Double.parseDouble(hField.getText()) - c;
            if(shape.startX < shape.endX) {
                shape.endX += d/2;
                shape.startX -= d/2;
            }
            else {
                shape.endX -= d/2;
                shape.startX += d/2;
            }
        }

        drawTemporaryRectangle(lastShape);
        redrawShapes();
    }
}