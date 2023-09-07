import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PaintApp extends Application {
    // List to store drawn shapes
    private List<GeometricObject> shapes = new ArrayList<>();
    private Canvas canvas;
    private GeometricObject currentShape;
    private boolean drawCircle = true; // Flag to determine if drawing circles

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Creating canvas and graphics context
        canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Handling mouse press on canvas
        canvas.setOnMousePressed(event -> {
            double x = event.getX();
            double y = event.getY();

            if (currentShape != null) {
                // Set shape's position and add to list
                currentShape.setX(x);
                currentShape.setY(y);
                shapes.add(currentShape);
                redraw(gc); // Redraw canvas with updated shapes
                currentShape = null; // Reset current shape
            }
        });

        // Dropdown for selecting shape
        ComboBox<String> shapeComboBox = new ComboBox<>();
        shapeComboBox.getItems().addAll("Circle", "Square");
        shapeComboBox.setPromptText("Select Shape");

        // Color picker for selecting color
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setPromptText("Select Color");

        // Grid for entering shape location
        GridPane locationInputGrid = new GridPane();
        locationInputGrid.setVgap(5);
        locationInputGrid.setHgap(10);

        // Labels and text fields for X and Y coordinates
        Label xLabel = new Label("X:");
        TextField xTextField = new TextField();
        xTextField.setPromptText("Enter X");
        locationInputGrid.add(xLabel, 0, 0);
        locationInputGrid.add(xTextField, 1, 0);

        Label yLabel = new Label("Y:");
        TextField yTextField = new TextField();
        yTextField.setPromptText("Enter Y");
        locationInputGrid.add(yLabel, 2, 0);
        locationInputGrid.add(yTextField, 3, 0);

        // Label and combo box for selecting size
        Label sizeLabel = new Label("Size:");
        ComboBox<Integer> sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(20, 30, 40, 50);
        sizeComboBox.setPromptText("Select Size");

        // Label for displaying error messages
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-font-size: 16; -fx-text-fill: red; -fx-alignment: center;");

        // Button for drawing shapes
        Button drawButton = new Button("Draw");
        drawButton.setOnAction(event -> {
            // Retrieve user inputs
            String selectedShape = shapeComboBox.getValue();
            Color selectedColor = colorPicker.getValue();
            Integer selectedSize = sizeComboBox.getValue();

            String xInput = xTextField.getText();
            String yInput = yTextField.getText();

            try {
                double x = Double.parseDouble(xInput);
                double y = Double.parseDouble(yInput);

                if (selectedShape != null && selectedColor != null && selectedSize != null) {
                    // Create appropriate shape based on user selection
                    if (selectedShape.equals("Circle")) {
                        currentShape = new Circle(x, y, selectedColor, selectedSize);
                    } else {
                        currentShape = new Square(x, y, selectedColor, selectedSize);
                    }

                    shapes.add(currentShape); // Add shape to list
                    redraw(gc); // Redraw canvas with new shape
                    errorLabel.setText(""); // Clear error label
                }
            } catch (NumberFormatException e) {
                showErrorLabel("Numeric value expected.", "X", errorLabel);
            } catch (IllegalArgumentException e) {
                showErrorLabel(e.getMessage(), "Size", errorLabel);
            }
        });

        // Organizing shape controls horizontally
        HBox shapeControls = new HBox(shapeComboBox, colorPicker, locationInputGrid, sizeLabel, sizeComboBox);
        shapeControls.setSpacing(10);

        // Button for drawing shapes
        HBox buttons = new HBox(drawButton);
        buttons.setSpacing(10);

        // Organizing main controls vertically
        VBox mainControls = new VBox(shapeControls, buttons, errorLabel);
        mainControls.setSpacing(10);

        // Creating title label
        Label titleLabel = new Label("Press Draw or Click Canvas for a Circle");
        titleLabel.setStyle("-fx-font-size: 18;");
        titleLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));

        // Organizing title label
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(javafx.geometry.Pos.CENTER);

        // Creating root layout
        BorderPane root = new BorderPane();
        root.setTop(titleBox);
        root.setCenter(canvas);
        root.setBottom(mainControls);

        root.setStyle("-fx-background-color: lightgray;");

        // Creating the scene
        Scene scene = new Scene(root, 800, 650);
        primaryStage.setTitle("Simple Drawing App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Redraw canvas with all shapes
    private void redraw(GraphicsContext gc) {
        gc.setFill(Color.WHITE); // Set the desired canvas background color
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Fill the entire canvas with the background color

        // Draw all shapes from the list
        for (GeometricObject shape : shapes) {
            shape.draw(gc);
        }
    }

    // Display error message in the error label
    private void showErrorLabel(String errorMessage, String fieldName, Label errorLabel) {
        errorLabel.setText(fieldName + ": " + errorMessage);
    }

    // GeometricObject, Circle, and Square classes should be defined here.
    // Existing code...
}
