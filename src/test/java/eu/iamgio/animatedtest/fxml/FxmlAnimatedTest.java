package eu.iamgio.animatedtest.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

import static eu.iamgio.animatedtest.TestUtil.randomColor;

public class FxmlAnimatedTest extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Animated.fxml")));
        Scene scene = new Scene(root, 650, 500);

        primaryStage.setTitle("FXML");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button updateButton = (Button) Objects.requireNonNull(root.lookup("#button"));
        Rectangle rectangle = (Rectangle) Objects.requireNonNull(root.lookup("#rectangle"));

        Random random = new Random();

        updateButton.setOnAction(e -> {
            rectangle.setFill(randomColor(random));

            GaussianBlur blur = (GaussianBlur) rectangle.getEffect();
            blur.setRadius(random.nextInt(100));
        });
    }
}