package eu.iamgio.animatedswitchertest;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import eu.iamgio.animated.AnimatedSwitcher;
import eu.iamgio.animated.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

// This demo dynamically generates random rectangles and plays a transition upon attaching it to the screen.
// A zoom-in is played on the 'new' rectangle, while the 'old' one disappears with a zoom-out animation.

public class AnimatedSwitcherTest extends Application {

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        // Setup switcher and attach it to the root
        AnimatedSwitcher switcher = new AnimatedSwitcher(new Animation(new ZoomIn()).setSpeed(2.5), new Animation(new ZoomOut()));
        root.getChildren().add(switcher);

        // Setup timeline
        Timeline timeline = new Timeline();
        startTimeline(scene, timeline, switcher);

        // Show
        primaryStage.setTitle("AnimatedSwitcher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This is called every second
    private void startTimeline(Scene scene, Timeline timeline, AnimatedSwitcher switcher) {
        // Generate a random rectangle
        Pane pane = new Pane(randomRectangle());
        // Center it
        center(pane, scene);
        // Update the child (plays the transition)
        switcher.setChild(pane);
        // Calls the same function after a 1s delay
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> startTimeline(scene, timeline, switcher)));
        timeline.playFromStart();
    }

    // Generates a random rectangle
    private Rectangle randomRectangle() {
        Random random = new Random();
        // Width and height go from 100 to 300
        double width = random.nextInt(200) + 100;
        double height = random.nextInt(200) + 100;
        // R, G and B values in 0.5-1 range
        Color color = new Color((random.nextInt(5) + 5) / 10F, (random.nextInt(5) + 5) / 10F, (random.nextInt(5) + 5) / 10F, 1);
        return new Rectangle(width, height, color);
    }

    // Centers a node (rectangles wrapped in Panes)
    private void center(Region parent, Scene scene) {
        parent.layoutXProperty().bind(scene.widthProperty().divide(2).subtract(parent.widthProperty().divide(2)));
        parent.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(parent.heightProperty().divide(2)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
