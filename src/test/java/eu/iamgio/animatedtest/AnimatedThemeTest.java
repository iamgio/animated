package eu.iamgio.animatedtest;

import eu.iamgio.animated.AnimatedThemeSwitcher;
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

import static eu.iamgio.animatedtest.TestUtil.center;

// This demo animates a theme switch.

public class AnimatedThemeTest extends Application {

    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, 650, 500);
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
        AnimatedThemeSwitcher themeSwitcher = AnimatedThemeSwitcher.of(scene);
        themeSwitcher.setTheme("/themes/light.css");

        // Setup timeline
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e -> themeSwitcher.animateTheme("/themes/dark.css")));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), e -> themeSwitcher.animateTheme("/themes/light.css")));
        timeline.play();

        // Show
        primaryStage.setTitle("AnimatedThemeSwitcher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
