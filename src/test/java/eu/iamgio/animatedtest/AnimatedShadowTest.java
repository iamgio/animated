package eu.iamgio.animatedtest;

import eu.iamgio.animated.binding.Animated;
import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.binding.presets.AnimatedDropShadow;
import eu.iamgio.animated.transition.AnimatedLabel;
import eu.iamgio.animated.transition.AnimationPair;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import static eu.iamgio.animatedtest.TestUtil.center;

// This demo animates a button's shadow radius and color on pressed and hover events.
// An animated text (via AnimatedSwitcher) flags the button's status as well.

public class AnimatedShadowTest extends Application {

    private static final int RADIUS_PRESSED = 100, RADIUS_HOVER = 60, RADIUS_DEFAULT = 20;
    private static final Color COLOR_PRESSED = Color.SLATEBLUE, COLOR_HOVER = Color.CRIMSON, COLOR_DEFAULT = Color.TEAL;
    private static final String TEXT_PRESSED = "PRESSED", TEXT_HOVER = "HOVER", TEXT_DEFAULT = "";

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        // Create button
        Button button = new Button("Animated shadow");
        center(button, scene);

        // Setup animated text label
        AnimatedLabel label = new AnimatedLabel(AnimationPair.fade().setSpeed(1, 2));

        label.setLabelFactory(text -> {
            Label newLabel = new Label(text);
            newLabel.prefWidthProperty().bind(scene.widthProperty());
            newLabel.setAlignment(Pos.CENTER);
            newLabel.setStyle("-fx-font-size: 18; -fx-padding: 60");
            return newLabel;
        });

        // Create effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(0);
        button.setEffect(shadow);

        // Set listeners

        button.pressedProperty().addListener((observable, oldValue, isPressed) -> {
            shadow.setRadius(isPressed ? RADIUS_PRESSED : RADIUS_HOVER);
            shadow.setColor(isPressed ? COLOR_PRESSED : COLOR_HOVER);
            label.setText(isPressed ? TEXT_PRESSED : TEXT_HOVER);
        });

        button.hoverProperty().addListener((observable, oldValue, isHover) -> {
            shadow.setRadius(isHover ? RADIUS_HOVER : RADIUS_DEFAULT);
            shadow.setColor(isHover ? COLOR_HOVER : COLOR_DEFAULT);
            label.setText(isHover ? TEXT_HOVER : TEXT_DEFAULT);
        });

        // Setup animation
        Animated animated = new Animated(button, new AnimatedDropShadow.Color(), new AnimatedDropShadow.Radius())
                .custom(settings -> settings.withDuration(Duration.millis(200)).withCurve(Curve.EASE_OUT_SINE));

        root.getChildren().addAll(label, animated);

        // Show
        primaryStage.setTitle("AnimatedDropShadow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}