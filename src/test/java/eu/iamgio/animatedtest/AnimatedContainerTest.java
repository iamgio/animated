package eu.iamgio.animatedtest;

import eu.iamgio.animated.AnimatedVBox;
import eu.iamgio.animated.AnimationPair;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static eu.iamgio.animatedtest.TestUtil.randomRectangle;

// This demo animates the content of multi-children containers, such as VBoxes.

public class AnimatedContainerTest extends Application {


    @Override
    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        AnimatedVBox vBox = new AnimatedVBox(AnimationPair.fade());

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
        primaryStage.setTitle("AnimatedContainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
