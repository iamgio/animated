package eu.iamgio.animatedtest.fxml;

import eu.iamgio.animated.binding.label.AnimatedIntValueLabel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

public class FxmlAnimatedValueLabelTest extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AnimatedValueLabel.fxml")));
        Scene scene = new Scene(root, 650, 500);

        primaryStage.setTitle("FXML");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button updateButton = (Button) Objects.requireNonNull(root.lookup("#button"));

        AnimatedIntValueLabel label = (AnimatedIntValueLabel) Objects.requireNonNull(root.lookup("#label"));

        Random random = new Random();

        updateButton.setOnAction(e -> label.setValue(random.nextInt(100)));
    }
}