package eu.iamgio.animatedtest;

import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOutUp;
import eu.iamgio.animated.AnimatedVBox;
import eu.iamgio.animated.Animation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static eu.iamgio.animatedtest.TestUtil.randomRectangle;

// This demo animates the content of multi-children containers, such as VBoxes.

public class AnimatedListTest extends Application {


    @Override
    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        AnimatedVBox vBox = new AnimatedVBox(new Animation(new FadeInUp()).setSpeed(3), new Animation(new FadeOutUp()).setSpeed(2));

        root.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.SECONDARY && !vBox.getChildren().isEmpty()) {
                vBox.getChildren().remove(vBox.getChildren().size() - 1);
            } else {
                Rectangle rectangle = randomRectangle();
                rectangle.setWidth(100);
                rectangle.setHeight(100);
                vBox.getChildren().add(rectangle);
            }
        });

        root.getChildren().add(vBox);

        // Show
        primaryStage.setTitle("AnimatedList");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
