package eu.iamgio.animatedtest;

import eu.iamgio.animated.transition.AnimatedSwitcher;
import eu.iamgio.animated.transition.AnimationPair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import static eu.iamgio.animatedtest.TestUtil.*;

// This demo animates a root switch within the same scene.

public class AnimatedRootSwitchTest extends Application {

    private int index = 0;

    public void start(Stage primaryStage) {
        // Setup scene
        AnimatedSwitcher switcher = new AnimatedSwitcher(AnimationPair.fadeDown());
        Scene scene = new Scene(switcher, SCENE_WIDTH, SCENE_HEIGHT);

        // Setup timeline
        Timeline timeline = new Timeline();
        startTimeline(scene, timeline, switcher);

        // Show
        primaryStage.setTitle("AnimatedRootSwitch");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This is called every 2 seconds
    private void startTimeline(Scene scene, Timeline timeline, AnimatedSwitcher switcher) {
        // Generate a new root
        StackPane root = new StackPane();
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

        Color bgColor = randomColor();
        root.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));

        Label text = new Label("Root no." + index);
        text.setTextFill(complementaryColor(bgColor));
        text.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        StackPane.setAlignment(text, Pos.CENTER);
        root.getChildren().add(text);

        // Update the child (plays the transition if this is not the first iteration)
        if(index++ > 0) {
            switcher.setChild(root);
        } else {
            switcher.of(root);
        }

        // Calls the same function after a 2s delay
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e -> startTimeline(scene, timeline, switcher)));
        timeline.playFromStart();
    }

    public static void main(String[] args) {
        launch(args);
    }
}