package eu.iamgio.animatedtest;

import eu.iamgio.animated.AnimatedMulti;
import eu.iamgio.animated.AnimationSettings;
import eu.iamgio.animated.property.AnimatedProperty;
import eu.iamgio.animated.property.DoublePropertyWrapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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

        Rectangle rectangle = randomRectangle();
        Pane pane = new Pane(rectangle);
        center(pane, scene);

        // Bind rectangle size to pane size
        pane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
        rectangle.widthProperty().bind(pane.prefWidthProperty());
        rectangle.heightProperty().bind(pane.heightProperty());

        // Setup the node and attach it to the root
        AnimatedMulti animated = new AnimatedMulti(pane,
                new AnimatedProperty<>(new DoublePropertyWrapper(pane.prefWidthProperty()), new AnimationSettings().withDuration(Duration.seconds(.8))),
                new DoublePropertyWrapper(pane.prefHeightProperty()),
                new DoublePropertyWrapper(pane.opacityProperty())
        );
        root.getChildren().add(animated);

        // Setup the timeline
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            pane.setPrefSize(500, 200);
            pane.setOpacity(.2);
        }));
        timeline.playFromStart();

        // Show
        primaryStage.setTitle("Animated");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
