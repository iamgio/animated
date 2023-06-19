package eu.iamgio.animatedtest;

import eu.iamgio.animated.binding.Animated;
import eu.iamgio.animated.binding.AnimationSettings;
import eu.iamgio.animated.binding.presets.AnimatedLayout;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

// This demo anchors a label to each corner and animates their position changes.

public class AnimatedLayoutTest extends Application {

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);
        scene.getStylesheets().add("/themes/dark.css");
        root.getStyleClass().add("layout-test");

        // Bind root size to scene size
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

        // Initialize a label for each corner
        Label topLeft = new Label("Top left");
        Label topRight = new Label("Top right");
        Label bottomLeft = new Label("Bottom left");
        Label bottomRight = new Label("Bottom right");

        // Initialize animation settings
        AnimationSettings settings = new AnimationSettings()
                .withDuration(new Duration(200));

        // Add nodes
        root.getChildren().addAll(
                new Animated(topLeft, new AnimatedLayout(topLeft, root, Pos.TOP_LEFT)).withSettings(settings),
                new Animated(topRight, new AnimatedLayout(topRight, root, Pos.TOP_RIGHT)).withSettings(settings),
                new Animated(bottomLeft, new AnimatedLayout(bottomLeft, root, Pos.BOTTOM_LEFT)).withSettings(settings),
                new Animated(bottomRight, new AnimatedLayout(bottomRight, root, Pos.BOTTOM_RIGHT)).withSettings(settings)
        );

        // Show
        primaryStage.setTitle("AnimatedLayout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
