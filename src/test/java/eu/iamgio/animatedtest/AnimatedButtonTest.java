package eu.iamgio.animatedtest;

import eu.iamgio.animated.binding.Animated;
import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import eu.iamgio.animated.common.Curve;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static eu.iamgio.animatedtest.TestUtil.center;

// This demo animates a button's background color and border radius on pressed and hover events.

public class AnimatedButtonTest extends Application {

    private static final Color COLOR_DEFAULT = Color.TRANSPARENT;
    private static final Color COLOR_HOVER = new Color(1, 1, 1, .5);
    private static final Color COLOR_PRESSED = new Color(1, 1, 1, .8);
    private static final double BASE_BACKGROUND_RADIUS = 60;
    private static final double BORDER_WIDTH = 4;

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);
        scene.getStylesheets().add("/themes/button.css");

        // Create button
        Button button = new Button("Animated button!");
        center(button, scene);

        // Initialize and bind the background color property
        SimpleObjectProperty<Color> backgroundColorProperty = new SimpleObjectProperty<>(COLOR_DEFAULT);

        button.backgroundProperty().bind(Bindings.createObjectBinding(
                () -> {
                    // The button's background/border radius depends on the color opacity
                    double radius = backgroundColorProperty.getValue().getOpacity() * BASE_BACKGROUND_RADIUS + 16;
                    button.setStyle("-fx-border-radius: " + String.format("%f", radius)); // Format to avoid exponential notations
                    // Bind our new background
                    return new Background(new BackgroundFill(backgroundColorProperty.getValue(), new CornerRadii(radius), null));
                },
                backgroundColorProperty // Binding target
        ));

        // Set listeners

        button.hoverProperty().addListener((observable, oldValue, isHover) ->
                backgroundColorProperty.set(isHover ? COLOR_HOVER : COLOR_DEFAULT)
        );

        button.pressedProperty().addListener((observable, oldValue, isPressed) ->
                backgroundColorProperty.set(isPressed ? COLOR_PRESSED : button.isHover() ? COLOR_HOVER : COLOR_DEFAULT)
        );

        // Setup animation

        Animated animated = new Animated(button, AnimationProperty.of(backgroundColorProperty))
                .custom(settings -> settings.withCurve(Curve.EASE_OUT));

        root.getChildren().add(animated);

        // Show
        primaryStage.setTitle("AnimatedButton");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}