package eu.iamgio.animatedtest;

import eu.iamgio.animated.transition.AnimatedThemeSwitcher;
import eu.iamgio.animated.transition.animations.clip.CircleClipOut;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import static eu.iamgio.animatedtest.TestUtil.*;

// This demo animates a theme switch.

public class AnimatedThemeTest extends Application {

    // Whether the current theme is 'light' (if false = 'dark')
    private boolean isLight = true;

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add("/themes/global.css");

        VBox box = new VBox();
        box.getStyleClass().add("central-box");
        center(box, scene);
        root.getChildren().add(box);

        Region icon = new Region();
        icon.getStyleClass().add("icon");
        box.getChildren().add(icon);

        Label title = new Label("Theme switch!");
        title.getStyleClass().add("title");
        box.getChildren().add(title);

        Label subtitle = new Label("Animated lets you create cool transitions.\nTry it out!");
        subtitle.getStyleClass().add("subtitle");
        box.getChildren().add(subtitle);

        // Setup theme
        scene.getStylesheets().add("/themes/light.css");
        AnimatedThemeSwitcher themeSwitcher = new AnimatedThemeSwitcher(scene, new CircleClipOut());
        themeSwitcher.init();

        // Setup timeline
        Timeline timeline = new Timeline();
        startTimeline(scene, timeline);

        // Show
        primaryStage.setTitle("AnimatedThemeSwitcher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This is called every two seconds
    private void startTimeline(Scene scene, Timeline timeline) {
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e -> {
            isLight ^= true;
            scene.getStylesheets().set(1, "/themes/" + (isLight ? "light" : "dark") + ".css");
            startTimeline(scene, timeline);
        }));
        timeline.playFromStart();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
