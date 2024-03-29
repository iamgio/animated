package eu.iamgio.animatedtest.fxml;

import eu.iamgio.animated.binding.value.AnimatedIntValueLabel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

import static eu.iamgio.animatedtest.TestUtil.SCENE_HEIGHT;
import static eu.iamgio.animatedtest.TestUtil.SCENE_WIDTH;

public class FxmlAnimatedValueLabelTest extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AnimatedValueLabel.fxml")));
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("FXML");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button updateButton = (Button) Objects.requireNonNull(root.lookup("#button"));

        AnimatedIntValueLabel label = (AnimatedIntValueLabel) Objects.requireNonNull(root.lookup("#label"));

        // The supplied random value changes the text opacity.
        label.animationValueProperty().addListener(o -> label.setOpacity(label.getAnimationValue() / 100.0));

        // Random value generation.
        Random random = new Random();
        updateButton.setOnAction(e -> label.setValue(random.nextInt(100)));
    }
}