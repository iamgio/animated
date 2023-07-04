package eu.iamgio.animatedtest;

import eu.iamgio.animated.binding.Curve;
import eu.iamgio.animated.binding.property.animation.AnimationProperty;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.Locale;

// This demo animates the content (money) of a label.

public class AnimatedCurrencyTest extends Application {

    private static final int AMOUNT_TO_ADD = 500;

    // Amount of money
    private int amount;

    @Override
    public void start(Stage primaryStage) {
        // Set up scene
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 500);

        // Set up label
        Label label = new Label();
        label.relocate(100, 100);
        label.setStyle("-fx-font-size: 20");

        // Create the animated property that stores the amount
        DoubleProperty amountProp = new SimpleDoubleProperty(this.amount);
        AnimationProperty.of(amountProp)
                .custom(settings -> settings.withCurve(Curve.EASE_IN_OUT_EXPO).withDuration(Duration.millis(1500)))
                .register(label);

        // Currency formatter
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        // Bind the label's text to the formatted amount
        label.textProperty().bind(Bindings.createObjectBinding(() -> formatter.format(amountProp.get()), amountProp));

        // Set up the button
        Button button = new Button("Add");
        button.relocate(100, 160);

        button.setOnAction(e -> {
            this.amount += AMOUNT_TO_ADD;
            amountProp.set(this.amount);
        });

        root.getChildren().addAll(label, button);

        // Show
        primaryStage.setTitle("AnimatedCurrency");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
