package eu.iamgio.animatedtest;

import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.binding.NewAnimated;
import eu.iamgio.animated.binding.presets.AnimatedOpacity;
import eu.iamgio.animated.binding.presets.AnimatedPrefSize;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
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

        // Set up the node and attach it to the root
        NewAnimated animated = new NewAnimated(pane,
                new AnimatedOpacity()
                        .custom(settings -> settings.withDuration(Duration.seconds(.4))),
                new AnimatedPrefSize()
                        .custom(settings -> settings.withDuration(Duration.seconds(.3)))
        ).custom(settings -> settings.withCurve(Curve.EASE_IN_OUT));

        root.getChildren().add(animated);

        // Set up the controls
        root.getChildren().add(setupControls(pane));

        // Show
        primaryStage.setTitle("Animated");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox setupControls(Pane pane) {
        HBox hbox = new HBox(8);
        hbox.setStyle("-fx-padding: 15;");
        hbox.setAlignment(Pos.CENTER);

        CheckBox checkBox = new CheckBox("Visible");
        checkBox.selectedProperty().addListener(o -> pane.setOpacity(checkBox.isSelected() ? 1 : 0));
        checkBox.setSelected(true);

        Pane spacer1 = new Pane();
        spacer1.setPrefWidth(5);

        Button widthMinus = new Button("Width -");
        widthMinus.setOnAction(e -> pane.setPrefWidth(pane.getPrefWidth() - 100));

        Button widthPlus = new Button("Width +");
        widthPlus.setOnAction(e -> pane.setPrefWidth(pane.getPrefWidth() + 100));

        Pane spacer2 = new Pane();
        spacer2.setPrefWidth(5);

        Button heightMinus = new Button("Height -");
        heightMinus.setOnAction(e -> pane.setPrefHeight(pane.getPrefHeight() - 100));

        Button heighPlus = new Button("Height +");
        heighPlus.setOnAction(e -> pane.setPrefHeight(pane.getPrefHeight() + 100));

        hbox.getChildren().addAll(checkBox, spacer1, widthMinus, widthPlus, spacer2, heightMinus, heighPlus);
        return hbox;
    }
}
