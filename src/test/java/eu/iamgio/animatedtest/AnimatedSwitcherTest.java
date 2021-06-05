package eu.iamgio.animatedtest;

import eu.iamgio.animated.AnimatedSwitcher;
import eu.iamgio.animated.AnimationPair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static eu.iamgio.animatedtest.TestUtil.center;
import static eu.iamgio.animatedtest.TestUtil.randomRectangle;

// This demo dynamically generates random rectangles and plays a transition upon attaching it to the screen.
// A zoom-in is played on the 'new' rectangle, while the 'old' one disappears with a zoom-out animation.

public class AnimatedSwitcherTest extends Application {

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        // Setup switcher and attach it to the root
        AnimatedSwitcher switcher = new AnimatedSwitcher(AnimationPair.zoom().setSpeed(2, .8));
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

    public static void main(String[] args) {
        launch(args);
    }
}
