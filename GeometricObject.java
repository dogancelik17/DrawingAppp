import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class GeometricObject implements Drawable {
    private double x;
    private double y;
    private Color fillColor;

    public GeometricObject(double x, double y, Color fillColor) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public abstract void draw(GraphicsContext gc);
}
