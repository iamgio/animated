package eu.iamgio.animatedtest;

import eu.iamgio.animated.AnimatedOpacity;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
        AnimatedOpacity animated = new AnimatedOpacity(pane).custom(settings -> settings.withDuration(Duration.seconds(.2)));
        root.getChildren().add(animated);

        // Setup the visibility check box
        CheckBox checkBox = new CheckBox("Visible");
        checkBox.selectedProperty().addListener(o -> pane.setOpacity(checkBox.isSelected() ? 1 : 0));
        checkBox.setSelected(true);
        checkBox.setStyle("-fx-padding: 15");
        root.getChildren().add(checkBox);

        // Show
        primaryStage.setTitle("Animated");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
