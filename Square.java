import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends GeometricObject {
    private double size;

    public Square(double x, double y, Color fillColor, double size) {
        super(x, y, fillColor);
        this.size = size;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(getFillColor());
        gc.fillRect(getX() - size / 2, getY() - size / 2, size, size);
    }
}
