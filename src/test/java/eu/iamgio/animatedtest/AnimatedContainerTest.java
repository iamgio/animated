package eu.iamgio.animatedtest;

import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOutUp;
import eu.iamgio.animated.AnimatedOpacity;
import eu.iamgio.animated.AnimatedVBox;
import eu.iamgio.animated.Animation;
import eu.iamgio.animated.Curve;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static eu.iamgio.animatedtest.TestUtil.randomRectangle;

// This demo animates the content of multi-children containers, such as VBoxes.

public class AnimatedContainerTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Setup scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        // Setup container
        AnimatedVBox vBox = new AnimatedVBox(new Animation(new FadeInUp()).setSpeed(3), new Animation(new FadeOutUp()).setSpeed(2));

        // Setup + button
        Button addButton = new Button("+ Add rectangle");
        addButton.relocate(400, 100);
        addButton.setOnAction(e -> {
            RectangleButton button = new RectangleButton();
            button.setOnMouseClicked(ev -> vBox.getChildren().remove(button));
            vBox.getChildren().add(button);
        });

        root.getChildren().addAll(vBox, addButton);

        // Show
        primaryStage.setTitle("AnimatedContainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class RectangleButton extends AnchorPane {

        // Random-colored rectangle with a "Click to remove" text that appears on hover on it.

        public RectangleButton() {
            Rectangle rectangle = randomRectangle();
            setPrefSize(100, 100);
            rectangle.setWidth(100);
            rectangle.setHeight(100);

            Label text = new Label("Click to remove");
            text.setPrefSize(100, 100);
            text.setAlignment(Pos.CENTER);
            text.setOpacity(0);

            hoverProperty().addListener((observable, oldValue, isHover) -> text.setOpacity(isHover ? 1 : 0));

            StackPane.setAlignment(text, Pos.CENTER);

            getChildren().addAll(
                    rectangle,
                    new AnimatedOpacity(text).custom(settings -> settings.withDuration(Duration.millis(150)).withCurve(Curve.EASE_IN_OUT))
            );
        }
    }
}
