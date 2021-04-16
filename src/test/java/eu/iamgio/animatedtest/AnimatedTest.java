package eu.iamgio.animatedtest;

import eu.iamgio.animated.Animated;
import eu.iamgio.animated.AnimatedOpacity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import static eu.iamgio.animatedtest.TestUtil.center;
import static eu.iamgio.animatedtest.TestUtil.randomRectangle;

// This demo dynamically changes several rectangle's properties.
// The Animated class lets the change play a transition.

public class AnimatedTest extends Application {

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        Pane pane = new Pane(randomRectangle());
        center(pane, scene);

        // Setup the node and attach it to the root
        Animated<Double> animated = new AnimatedOpacity(pane);
        root.getChildren().add(animated);

        // Setup the timeline
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> pane.setOpacity(.2)));
        timeline.playFromStart();

        // Show
        primaryStage.setTitle("Animated");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
