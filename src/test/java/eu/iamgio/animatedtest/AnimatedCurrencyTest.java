package eu.iamgio.animatedtest;

import eu.iamgio.animated.binding.value.AnimatedValueLabel;
import eu.iamgio.animated.common.Curve;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.Locale;

import static eu.iamgio.animatedtest.TestUtil.SCENE_HEIGHT;
import static eu.iamgio.animatedtest.TestUtil.SCENE_WIDTH;

// This demo animates the content (money) of a label.

public class AnimatedCurrencyTest extends Application {

    private static final int AMOUNT_TO_ADD = 500;

    @Override
    public void start(Stage primaryStage) {
        // Set up scene
        Pane root = new Pane();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        // Currency formatter
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        // Set up label
        AnimatedValueLabel<Double> label = new AnimatedValueLabel<>(0.0)
                .custom(settings -> settings.withCurve(Curve.EASE_IN_OUT_EXPO).withDuration(Duration.millis(1500)));

        label.setTextMapper(formatter::format);
        label.relocate(100, 100);
        label.setStyle("-fx-font-size: 20");

        // Set up the button
        Button button = new Button("Add");
        button.relocate(100, 160);

        button.setOnAction(e -> label.setValue(label.getValue() + AMOUNT_TO_ADD));

        root.getChildren().addAll(label, button);

        // Show
        primaryStage.setTitle("AnimatedCurrency");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
