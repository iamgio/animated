package eu.iamgio.animatedtest;

import animatefx.animation.FadeInUp;
import animatefx.animation.SlideOutLeft;
import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.binding.NewAnimated;
import eu.iamgio.animated.binding.presets.AnimatedOpacity;
import eu.iamgio.animated.transition.AnimationPair;
import eu.iamgio.animated.transition.container.AnimatedVBox;
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
        AnimatedVBox vBox = new AnimatedVBox(new AnimationPair(new FadeInUp(), new SlideOutLeft()).setSpeed(3, 3));

        // Also try HBox:
        // AnimatedHBox hBox = new AnimatedHBox(new AnimationPair(new FadeInDown(), new SlideOutUp()).setSpeed(3, 3));

        // Setup + buttons

        Button topButton = new Button("+ Add on top");
        topButton.setPrefWidth(150);
        topButton.relocate(400, 100);
        topButton.setOnAction(e -> vBox.getChildren().add(0, new RectangleButton(vBox)));

        Button bottomButton = new Button("+ Add on bottom");
        bottomButton.setPrefWidth(150);
        bottomButton.relocate(400, 150);
        bottomButton.setOnAction(e -> vBox.getChildren().add(new RectangleButton(vBox)));

        root.getChildren().addAll(vBox, topButton, bottomButton);

        // Show
        primaryStage.setTitle("AnimatedContainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class RectangleButton extends AnchorPane {

        // Random-colored rectangle with a "Click to remove" text that appears on hover on it.

        public RectangleButton(Pane parent) {
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
                    new NewAnimated(text, new AnimatedOpacity())
                            .custom(settings -> settings.withDuration(Duration.millis(150)).withCurve(Curve.EASE_IN_OUT))
            );

            setOnMouseClicked(ev -> parent.getChildren().remove(this));
        }
    }
}
